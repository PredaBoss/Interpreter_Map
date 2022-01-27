package Controller;

import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;
import Model.Stmt.IStmt;
import Model.Containers.MyIStack;
import Model.PrgState;
import Repository.Repository;
import Model.Value.Value;
import Model.Value.RefValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final Repository repo;
    private final boolean display;
    ExecutorService executor;

    public Controller(Repository repo){
        this.repo= repo;
        this.display=false;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgStates) throws StmtException, ContainerException, ExpException, IOException, InterruptedException {
        for(PrgState prgState: prgStates)
            repo.logPrgStateExec(prgState);

        List<Callable<PrgState>> callList = prgStates.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                . map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgStates.addAll(newPrgList);

        for(PrgState prgState: prgStates)
            repo.logPrgStateExec(prgState);

        repo.setPrgList(prgStates);
    }

    public void beforeSteps() throws FileNotFoundException {
        this.repo.clearFile();
        executor = Executors.newFixedThreadPool(2);
    }

    public void afterOneStep(){
        List<PrgState>  prgList=removeCompletedPrg(repo.getPrgList());
        if(prgList.size() == 0)
            return;
        PrgState prgState = prgList.get(0);
        prgState.getHeap().setContent(
                safeGarbageCollector(
                        getAddrFromSymTable(
                                prgList.stream()
                                        .flatMap(state -> state.getSymTable().getContent().values().stream())
                                        .collect(Collectors.toList())),
                        prgState.getHeap().getContent()));
        // update the repository state
        repo.setPrgList(prgList);
    }

    public void allStep() throws ContainerException, StmtException, ExpException, IOException, InterruptedException {
        this.repo.clearFile();
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState>  prgList=removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){

            PrgState prgState = prgList.get(0);
            prgState.getHeap().setContent(
                        safeGarbageCollector(
                                getAddrFromSymTable(
                                        prgList.stream()
                                        .flatMap(state -> state.getSymTable().getContent().values().stream())
                                        .collect(Collectors.toList())),
                                prgState.getHeap().getContent()));

            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }

    public List<PrgState> getPrgStates(){
        return this.repo.getPrgList();
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || itContainsAddress(heap.values(),e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    boolean itContainsAddress(Collection<Value> values,Integer address){
        for(Value value:values){
            if((value instanceof RefValue) && address == ((RefValue)value).getAddr())
                return true;
        }
        return false;
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
}

package View.Cli;

import Controller.Controller;
import Exceptions.ContainerException;
import Exceptions.ExpException;
import Exceptions.StmtException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller ctr;

    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }

    @Override
    public void execute() {
        try{
            ctr.allStep();
        }
        catch(ExpException | StmtException | ContainerException | IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

}

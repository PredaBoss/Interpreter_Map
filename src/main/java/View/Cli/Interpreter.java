package View.Cli;

import Controller.Controller;
import Model.Containers.MyDictionary;
import Model.Exp.*;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import View.Data;

import java.util.List;

class Interpreter {

    public static void main(String[] args) {
        try {
            List<IStmt> programs = Data.getPrograms();

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));

            int cnt = 0;
            for(IStmt program:programs){
                program.typecheck(new MyDictionary<>());
                menu.addCommand(new RunExample(Integer.toString(cnt++),program.toString(),new Controller(new Repository(new PrgState(program),"log.txt"))));
            }

            menu.show();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

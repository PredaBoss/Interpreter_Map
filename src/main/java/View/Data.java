package View;

import Model.Exp.*;
import Model.Stmt.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<IStmt> getPrograms(){
        List<IStmt> programs = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        programs.add(ex1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        programs.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        programs.add(ex3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/test.in"))), new CompStmt(new OpenRFileStmt(new ValueExp(new StringValue("varf"))), new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new ValueExp(new StringValue("varf")))))))))));
        programs.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new RHExp(new RHExp(new VarExp("a")))))))));
        programs.add(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))), new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), ">", new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))), new PrintStmt(new VarExp("v")))));
        programs.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new RHExp(new VarExp("v"))), new CompStmt(new WHStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp('+', new RHExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        programs.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),new CompStmt(new WHStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new RHExp(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new RHExp(new VarExp("a")))))))));
        programs.add(ex8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v1",new RefType(new IntType())),new CompStmt(new VarDeclStmt("v2",new RefType(new IntType())),new CompStmt(new VarDeclStmt("v3",new RefType(new IntType())),new CompStmt(new VarDeclStmt("cnt",new IntType()),new CompStmt(new NewStmt("v1",new ValueExp(new IntValue(2))),new CompStmt(new NewStmt("v2",new ValueExp(new IntValue(3))),new CompStmt(new NewStmt("v3",new ValueExp(new IntValue(4))),new CompStmt(new NewLatchStmt("cnt",new RHExp(new VarExp("v2"))),new CompStmt(new ForkStmt(new CompStmt(new WHStmt("v1",new ArithExp('*',new RHExp(new VarExp("v1")),new ValueExp(new IntValue(10)))),new CompStmt(new PrintStmt(new RHExp(new VarExp("v1"))),new CountDownStmt("cnt")))),new CompStmt(new ForkStmt(new CompStmt(new WHStmt("v2",new ArithExp('*',new RHExp(new VarExp("v2")),new ValueExp(new IntValue(10)))),new CompStmt(new PrintStmt(new RHExp(new VarExp("v2"))),new CountDownStmt("cnt")))),new CompStmt(new ForkStmt(new CompStmt(new WHStmt("v3",new ArithExp('*',new RHExp(new VarExp("v3")),new ValueExp(new IntValue(10)))),new CompStmt(new PrintStmt(new RHExp(new VarExp("v3"))),new CountDownStmt("cnt")))),new CompStmt(new AwaitStmt("cnt"),new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),new CompStmt(new CountDownStmt("cnt"),new PrintStmt(new ValueExp(new IntValue(100)))))))))))))))));
        programs.add(ex9);

        return programs;
    }
}

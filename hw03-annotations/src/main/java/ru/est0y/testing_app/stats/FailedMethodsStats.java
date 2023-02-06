package ru.est0y.testing_app.stats;

public class FailedMethodsStats extends Stats{
    @Override
    public String toString(){
        String result="";
        for (Exception e:failed){
            result=result.concat("\n"+e.getMessage());
        }
        return result;
    }
}
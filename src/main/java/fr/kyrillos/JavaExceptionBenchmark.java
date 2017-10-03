package fr.kyrillos;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.util.concurrent.TimeUnit.SECONDS;


@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1, timeUnit = SECONDS)
@Fork(5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class JavaExceptionBenchmark {

    public static final String[] ARGS = new String[]{
            "1", "33", "450" , "89", "45", "6" ,"34", "14", "@@@",
            "1", "33", "450" , "89", "45", "6" ,"34", "14", "54",
            "1", "33", "450" , "a", "45", "6" ,"34", "14", "54",
            "1", "33", "450" , "89", "45", "6" ,"45.6", "14", " ",
            "1", "33", "450" , "89", "45", "6" ,"34", "14", "54",
            "1", "FF", "450" , "89", "R", "6" ,"34", "14", "54",
            "1", "33", "450" , "89", "45", "6" ,"34", "14", "54",
            "1", "33", "FG" , "89", "45", "6" ,"34", "14", "54",
            "1", "33", "450" , "89", "B", "6" ,"34", "14", "54"
    };

    private static final Pattern PATTERN = Pattern.compile("\\d+");

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder().include(".*" + JavaExceptionBenchmark.class.getSimpleName() + ".*").build()).run();
    }


    @Benchmark
    public void testWithoutChecks(){
        for (String arg : ARGS) {
            parseWithException(arg);
        }
    }

    @Benchmark
    public void testWithChecks(){
        for (String arg : ARGS) {
            if (hasDigits(arg)){
                parseWithException(arg);
            }
        }
    }

    public Integer parseWithException(String s){
        try{
            return Integer.valueOf(s);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public boolean hasDigits(String s){
        return PATTERN.matcher(s).matches();
    }
}

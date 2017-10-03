package fr.kyrillos;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JavaFinalVarBenchmark {

    public static final int LOOP = 1000;
    private NotInlinableClass notInlinable;
    private InlinableClass inlinable;

    @Setup
    public void setup(){
        notInlinable  = new NotInlinableClass(10);
        inlinable = new InlinableClass(10);
    }



    public void testFinalValueAccess(){
        long count = 0;
        for (int i = 0; i < LOOP; i++) {
            count += inlinable.getFinalValue();
        }
    }

    public void testMutableValueAccess(){
        long count = 0;
        for (int i = 0; i < LOOP; i++) {
            count += inlinable.getMutableValue();
        }
    }



    public static class NotInlinableClass{

        private final long finalValue;
        private long mutableValue;

        public NotInlinableClass(long value) {
            this.finalValue = value;
            this.mutableValue = value;
        }

        public long getFinalValue() {
            return finalValue;
        }

        public long getMutableValue() {
            return mutableValue;
        }
    }


    public static class InlinableClass{

        private final long finalValue;
        private long mutableValue;

        public InlinableClass(long value) {
            this.finalValue = value;
            this.mutableValue = value;
        }

        public long getFinalValue() {
            return finalValue;
        }

        public long getMutableValue() {
            return mutableValue;
        }
    }
}

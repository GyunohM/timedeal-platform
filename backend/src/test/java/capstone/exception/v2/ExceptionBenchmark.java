package capstone.exception.v2;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class ExceptionBenchmark {


    // 실제 RestApiException과 내부 구조(ErrorCode 보유)는 100% 똑같은 테스트를 위한 예외
    // fillInStackTrace() 오버라이딩을 하지 않아 스택 트레이스를 무겁게 다 생성함
    private static class HeavyRestApiException extends RuntimeException {
        private final ErrorCode errorCode;

        public HeavyRestApiException(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }
        // 오버라이딩 없음
    }

    // 스택 트레이스 ON
    @Benchmark
    public void testExceptionWithStackTrace(Blackhole bh) {
        try {

            throw new HeavyRestApiException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            bh.consume(e);
        }
    }

    // 내가 만든 실제 프로덕션 예외 (스택 트레이스 OFF)
    @Benchmark
    public void testExceptionWithoutStackTrace(Blackhole bh) {
        try {
            throw new RestApiException(ErrorCode.BAD_REQUEST);
        } catch (Exception e) {
            bh.consume(e);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(ExceptionBenchmark.class.getSimpleName())
            .build();
        new Runner(opt).run();
    }
}

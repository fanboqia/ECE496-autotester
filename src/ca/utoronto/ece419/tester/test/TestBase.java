package ca.utoronto.ece419.tester.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class TestBase {
    protected boolean HIJACKIO = false;
    protected String testName = null;

    protected PrintStream out = null;
    protected PrintStream err = null;

    protected int score = -1;
    protected int maxScore = -1;
    protected int successCount = -1;
    protected Class testClass = null;
    protected Map<String, Method> testCases = null;
    protected Map<String, Boolean> testCaseResult = null;
    protected Map<String, Throwable> testCaseExceptions = null;

    protected TestBase(Class testClass, String testName) {
        this.err = System.err;
        this.out = System.out;

        this.testClass = testClass;
        this.testName = testName;

        this.score = 0;
        this.maxScore = 0;
        this.successCount = 0;
        this.testCases = new HashMap<>();
        this.testCaseResult = new HashMap<>();
        this.testCaseExceptions = new HashMap<>();

        TestCase ann = null;
        for(Method m : this.testClass.getDeclaredMethods()) {
            ann = m.getAnnotation(TestCase.class);
            if(ann != null) {
                if(m.getReturnType() != boolean.class) {
                    throw new RuntimeException("Testcase `" + m.getName() + "` from test `" + this.testClass.getName() + "` does not have a boolean return type.");
                }
                if(m.getParameterCount() > 0) {
                    throw new RuntimeException("Testcase `" + m.getName() + "` from test `" + this.testClass.getName() + "` should not accept any parameters.");
                }

                this.maxScore += ann.score();
                this.testCases.put(m.getName(), m);
            }
        }
    }

    /**
     * Callable before/after each test case
     */
    protected abstract void before(String methodName);
    protected abstract void after(String methodName);

    /**
     * Callable before/after the entire test
     */
    protected abstract void start();
    protected abstract void finish();

    public final TestBase run() {
        boolean isSuccess = false;
        Method testCase = null;
        this.start();
        for(String testCaseName : this.testCases.keySet()) {
            isSuccess = false;
            testCase = this.testCases.get(testCaseName);
            this.before(testCaseName);
            try {
                isSuccess = (Boolean) testCase.invoke(this);
            } catch (Exception e) {
                this.testCaseExceptions.put(testCaseName, e);
            }
            this.after(testCaseName);
            if(isSuccess) {
                this.score += ((TestCase) testCase.getAnnotation(TestCase.class)).score();
                this.successCount++;
            }
            this.testCaseResult.put(testCaseName, isSuccess);
        }
        this.finish();

        return this;
    }

    public final String getReport() {
        Method testCase = null;
        TestCase ann = null;
        Throwable t = null;
        StringBuilder builder = new StringBuilder();

        builder.append("*** ");
        builder.append(this.testName);
        builder.append(" ***\n");

        for(String testCaseName : this.testCases.keySet()) {
            testCase = this.testCases.get(testCaseName);
            ann = testCase.getAnnotation(TestCase.class);

            builder.append(" [ ");
            builder.append(this.testCaseResult.get(testCaseName) ? " P " : "*F*");
            builder.append(" ] ");
            builder.append(ann.name());
            builder.append("\n");

            if(ann.details().length() > 0) {
                builder.append("        ");
                builder.append(ann.details());
                builder.append("\n");
            }

            if(this.testCaseExceptions.containsKey(testCaseName)) {
                t = this.testCaseExceptions.get(testCaseName);
                builder.append("        *** ");
                builder.append(t.getCause().getMessage());
                int i = 3;
                while(t != null) {
                    builder.append("        ");
                    builder.append(String.join("", Collections.nCopies(i, "*")));
                    builder.append(" ");
                    builder.append(t.getClass().getName());
                    builder.append(": ");
                    builder.append(t.getMessage());
                    builder.append("\n");
                    t = t.getCause();
                    i++;
                }
            }
        }

        return builder.toString();
    }

    public final int getSuccessCount() {
        return this.successCount;
    }

    public final int getTotalCount() {
        return this.testCases.size();
    }

    public final int getScore() {
        return this.score;
    }

    public final int getMaxScore() {
        return this.maxScore;
    }

    protected final void hijackIO() {
        if(this.HIJACKIO) {
            System.setErr(new PrintStream(new OutputStream() {
                @Override
                public void write(int i) throws IOException {
                }
            }));
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int i) throws IOException {
                }
            }));
        }
    }

    protected final void releaseIO() {
        if(this.HIJACKIO) {
            System.setErr(this.err);
            System.setOut(this.out);
        }
    }
}

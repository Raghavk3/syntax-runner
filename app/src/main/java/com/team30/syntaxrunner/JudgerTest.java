package com.team30.syntaxrunner;

import java.util.List;
import java.util.Map;

public class JudgerTest {

    public static void main(String[] args) {
        DifficultyManager dm = new DifficultyManager(Difficulty.MEDIUM);

        Collector collector = new Collector();
        Judger judger = new Judger();

        // ----- Positive regression: ALWAYS_CORRECT should be 100% true -----
        TestCollector goodPlayer = new TestCollector(TestCollector.Mode.ALWAYS_CORRECT);
        int correctRuns = runBatch(dm, goodPlayer, collector, judger, 200);
        System.out.println("ALWAYS_CORRECT: " + correctRuns + " / 200 judged true");

        // ----- Random fuzz: most runs should be judged false -----
        TestCollector randomPlayer = new TestCollector(TestCollector.Mode.RANDOM);
        int luckyRuns = runBatch(dm, randomPlayer, collector, judger, 200);
        System.out.println("RANDOM:         " + luckyRuns + " / 200 judged true");
    }

    private static int runBatch(DifficultyManager dm,
                                TestCollector tc,
                                Collector collector,
                                Judger judger,
                                int times) {
        int passes = 0;
        for (int i = 0; i < times; i++) {
            Question q = new Question(dm.pickRandomQuestion());
            Map<Integer, List<String>> picks = tc.collectAnswers(q);
            Map<Integer, String> assembled = collector.assemble(picks);
            if (judger.judgeAll(q, assembled)) passes++;
        }
        return passes;
    }
}

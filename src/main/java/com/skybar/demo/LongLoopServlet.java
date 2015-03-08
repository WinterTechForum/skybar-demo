package com.skybar.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class LongLoopServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long before = System.nanoTime();
        AtomicLong counter = new AtomicLong();

        Thread[] threads = new Thread[100];
        for (int t = 0; t < 1; t++) {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread() {
                    @Override
                    public void run() {
                        counter.addAndGet(LongLoopServlet.loop());
                    }
                };
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        resp.getWriter().print("Counted " + counter.get() + " in: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-before) +"ms.");

    }

    public static long loop() {
        long l = 0;
        for (int i = 0; i < 200000; i++) {
            l += innerLoop();
        }
        return l;
    }

    private static long innerLoop() {
        long l = 0;
        for (int i = 0; i < 10; i++) {
            l++;
        }

        l += fastMethod();
        return l;
    }

    private static long fastMethod() {
        return fasterMethod();
    }

    private static long fasterMethod() {
        return Thread.currentThread().getId();
    }


}

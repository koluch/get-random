/**
 * --------------------------------------------------------------------
 * Copyright 2015 Nikolay Mavrenkov
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------
 * <p/>
 * Author:  Nikolay Mavrenkov <koluch@koluch.ru>
 * Created: 17.10.2015 01:25
 */
package ru.koluch.getRandom;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

public class RandomTextServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();

        Integer wordCountParam = getInt(req, "word_count", 100);
        Integer wordLengthParam = getInt(req, "word_length", 10);
        Integer wordCountSpreadParam = getInt(req, "word_count_spread", 0);
        Integer wordLengthSpreadParam = getInt(req, "word_length_spread", 0);

        Random random = new Random();
        Integer wordCount = rnd(random, wordCountParam, wordCountSpreadParam);
        for (int i = 0; i < wordCount; i++) {
            Integer wordLength = rnd(random, wordLengthParam, wordLengthSpreadParam);
            for (int j = 0; j < wordLength; j++) {
                writer.append((char) ('a' + (random.nextInt('z'-'a'+1))));
            }
            if(i<wordCountParam-1) writer.append(' ');
        }
    }


    private static Integer rnd(Random random, Integer middle, Integer spread) {
        return middle - spread + random.nextInt(spread * 2 + 1);
    }


    private static Integer getInt(HttpServletRequest req, String key, Integer def) {
        String parameter = req.getParameter(key);
        if(parameter!=null) {
            try {
                return Integer.valueOf(parameter);
            } catch (Throwable ignored){}
        }
        return def;
    }

}

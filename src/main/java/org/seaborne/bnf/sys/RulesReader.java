/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.seaborne.bnf.sys;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import org.seaborne.bnf.Grammar;
import org.seaborne.bnf.parser.javacc.EBNFParser;
import org.seaborne.bnf.parser.javacc.ParseException;

/**
 * Operations of {@Grammar} objects.
 */
public class RulesReader {

    public static Grammar parseFile(String filename) {
        try {
            InputStream input = new FileInputStream(filename);
            EBNFParser parser = new EBNFParser(input);
            parser.Unit();
            return parser.getGrammar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static Grammar parseString(String text) {
        EBNFParser parser = new EBNFParser(new StringReader(text));
        try {
            parser.Unit();
            return parser.getGrammar();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

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

package org.seaborne.bnf;

import java.io.StringReader;

import org.seaborne.bnf.parser.javacc.EBNFParser;
import org.seaborne.bnf.parser.javacc.ParseException;

public class MainEBNF {

    // [x]  Print EBNF
    //     Parentheses as input.
    // [ ] Calculate parenteses

    // More on ranges.
    //   Negative ranges [^...]

    // Plain ranges
    // {N,N}


    // [ ] Validation
    //    e.g. rule body names other rules not undef.

    // Range chars.
    // * Replace WORD with one character token Word() -- but need to do WS control?
    // * Lexical States

    // Multiline:
    // * Allow "\" as continuation marker end of line, then optional labels.
    // * Allow "/" as beginning of line continuation, then optional labels.
    // * Label required -> allow multiple line rules. But non-standard

    public static void main(String... args) throws Exception {
        one("""
                [1] A ::= B{2,3}
                [2] A ::= B{*}
                [2] A ::= B{2}
                [3] A ::= B{2,}
                [4] A ::= B{2,*}
                [5] A ::= B{,4}
                [6] A ::= B{,*}

                """);
//        // All structure features

        one("""
                // Comment
            [1] A ::= B
            [2] A ::= Z  ?

                        A ::= A2* | (A3 | A4)? | A5 A6 | A7
            C ::= #x0D*
            D ::=  [ #x11 - #x22 ] [^ 0-9]    //END
            A_B_C55 ::= X1 X2 | X3 "QS 1"
            A ::= ( A2 A3 ) | A5 A6 | A7
                A1 ::= B{2,3}
                A2 ::= Z B{*} | Y
                A3 ::= (B{2})*
                A4::= B{2,}
                A5::= B{2,*}
                A6::= B{,4}
                A7 ::= B{,*}
                A8 ::= B{*,*}

//            """);
    }

    private static void one(String text) {
        System.out.println("==== Input");
        System.out.println("         1         2         3         4");
        System.out.println("1234567890123456789012345678901234567890");
        System.out.println(text);
        EBNFParser parser = new EBNFParser(new StringReader(text));
        Grammar grammar;
        try {
            parser.Unit();
            System.out.println("Parse succeeded.");
            System.out.println();
            grammar = parser.getGrammar();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
            //e.printStackTrace();
        }
        Rules.printStructure(grammar);
        System.out.println("<><><><>");
        Rules.printEBNF(grammar);
        System.out.println();
    }
}

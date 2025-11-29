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
    // [ ] Calculate parentheses

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

    // Standard EBNF (ISO/IEC 14977) uses a semicolon (;) terminator
    // W3C ebnf::
    // ** Only in alternatives: for lines starting |
    //   i.e. allow (EOL)? `|`

    // https://www.bottlecaps.de/rex/
    // newline after ::=


    // 1. Go for "\" WS EOL == "\" is a pseudo element
    // 2. Only in alternatives: for lines starting |
    // 3. Allow "\" as continuation marker end of line

    // * Allow "\" as continuation marker end of line, then optional labels.


    // Sequence is actually a stack of Unary()
    // | ends the sequence
    // 2 Unary  U2 U1 "::=" is end last rule at U2 add uses U1 as production names.
    //    Tricky for modifiers "a*"
    //  == Word() lookahead.


    // LOOKAHEAD(4) Word-optLabel-word ::=
    // LOOKAHEAD(Peek rule) -- "syntactic lookahead"
    // LOOKAHEAD( { getToken(1).kind == C && getToken(2).kind != C } ) -- Semantic lookahead


    public static void main(String... args) throws Exception {
        // All structure features
        String str = """
                LONGNAME ::=
                    B1 B2
                    | A3
                    | D | C E
                [2] X ::= X1 | X2
                """;

//        one(cardinalityStr);
        one(str);
//        one(str);

    }
    public static void test() throws Exception {
        String cardinalityStr = """
                [1] A ::= B{2,3}
                [2] A ::= B{*}
                [2] A ::= B{2}
                [3] A ::= B{2,}
                [4] A ::= B{2,*}
                [5] A ::= B{,4}
                [6] A ::= B{,*}
                """;
        // All structure features
        String featureStr = """
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
                //
                """;
        String str = "";

//        one(cardinalityStr);
        one(featureStr);
//        one(str);
    }

    private static void one(String text) {
        System.out.println("==== Input");
        System.out.println("         1         2         3         4");
        System.out.println("1234567890123456789012345678901234567890");
        System.out.print(text);
        if ( ! text.endsWith("\n") )
            System.out.println();
        System.out.println();
        EBNFParser parser = new EBNFParser(new StringReader(text));
        try {
            parser.Unit();
            System.out.println("-- Parse succeeded.");
            System.out.println();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
            //e.printStackTrace();
        }
        Grammar grammar = parser.getGrammar();
        Rules.printStructure(grammar);
        System.out.println("<><><><>");
        Rules.printEBNF(grammar);
        System.out.println();
    }
}

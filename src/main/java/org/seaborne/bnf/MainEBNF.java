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

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import org.seaborne.bnf.parser.javacc.EBNFParser;
import org.seaborne.bnf.parser.javacc.ParseException;

public class MainEBNF {

    // [x] C-block comments.
    // [ ] Printing Quoted strings.
    // [ ] Micro-parse character ranges.
    // [ ] infix -
    // [ ] @terminals

    // [?] Switch to No SKIP - add WS() as needed.

    // [ ] A - B    with no precedence rules.
    // [ ] Parsing terminals
    // [x] /* Comments used in W3C. */
    // More range [ ] possibilities - any char list, inc \]
    //    [a-zA-Z], [#xN-#xN]
    //    [abc], [#xN#xN#xN]
    //    [^a-z], [^#xN-#xN]
    //    [^abc], [^#xN#xN#xN]
    //    and mixed ranges and enumerations
    //    [^#x00-#x20<>"{}|^`\]
    // [ ] Extension @terminals

    // Possibilities:
    // Terminal ";"

    /*
    #xN
    [a-zA-Z], [#xN-#xN]
    [abc], [#xN#xN#xN]
    [^a-z], [^#xN-#xN]
    [^abc], [^#xN#xN#xN]
    */
    // RE: <LBRACK>('^')?([^]]|"\]")*<RBRACK>

    // [x]  Print EBNF

    // [ ] Validation
    // All productions used (body) as defined (head)

    // Extensions and interpretations
    // + Production labels: "[ ]"
    // + Multiline
    // + Blanklines

    // == Multiline:
    // Standard EBNF (ISO/IEC 14977) uses a semicolon (;) terminator
    // W3C ebnf::
    //   ** Only in alternatives: for lines starting |
    //     i.e. allow (EOL)? `|`
    //     i.e. allow (EOL)? around ::=

    // From https://www.bottlecaps.de/rex/
    //     newline after ::=

    // ??
    // * Allow "\" as continuation marker end of line, then optional labels.

    public static void main(String... args) throws Exception {

        // Print with invisible primary

        if ( false )
        {
            String x1 = """
                    X ::= A - (B | C )
                    X ::= (A - B) | C
                    // Wrong - bind "-" more tightly. Primary? ie. not *
                    X ::= A - B | C
                    """;
            String x = """
                    X ::= A - B C
                    X ::= (A - B) C
                    X ::= A - (B C)
                    """;

            one(x);
            System.exit(0);
        }

        // [^ char ranges
        //String fn1 = "/home/afs/W3C/rdf-star-wg/rdf-turtle/spec/turtle.bnf";

        String fn1 = "Examples/rdf-turtle.bnf";
        String fn2 = "Examples/sparql-query.bnf";


        String fnx1 = "Examples/turtle.ebnf";
        String fnx2 = "Examples/SPARQL.ebnf";
        String fnx3 = "Examples/Java.ebnf";
        // All structure features
        // No escapes inside []- use #5D for ]

        // Missing : general - (like alt)
        String str = """
IRIREF ::= '<' ([^<>"{}|^`\\]-[#x00-#x20])* '>'
               """;

//        one(str);
//        System.exit(0);


        String strCR = """
//Range1 ::=    #xN
Range1 ::=    [a-zA-Z]
Range1 ::=    [#xN-#xN]
Range1 ::=    [abc]
Range1 ::=    [#xN#xN#xN]
Range1 ::=    [^a-z]
Range1 ::=    [^#xN-#xN]
Range1 ::=    [^abc]
Range1 ::=    [^#xN#xN#xN]
                D ::=  [#x11-#x22][^0-9]
                Z ::= [^ 0 - 9  ]
                Z ::= [^\\]ABC]
                Z ::= [^\\\\Z]
                """;

//        parse(str);
//        System.exit(0);


//        one(cardinalityStr);
//        one(str);

        parseFile(fn1);
        parseFile(fn2);


//        Grammar grammar = parseFile(fn1);
//        Rules.printStructure(grammar);
//        System.out.println("<><><><>");
//        Rules.printEBNF(grammar);
//        System.out.println();
    }

    public static Grammar parseFile(String filename) throws Exception {
        System.out.println("File: "+filename);
        InputStream input = new FileInputStream(filename);
        EBNFParser parser = new EBNFParser(input);
        try {
            parser.Unit();
            System.out.println("-- Parse succeeded.");
            System.out.println();
            return parser.getGrammar();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
            //e.printStackTrace();
        }
//        Grammar grammar = parser.getGrammar();
//        Rules.printStructure(grammar);
//        System.out.println("<><><><>");
//        Rules.printEBNF(grammar);
//        System.out.println();

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

    private static void parse(String text) {
        EBNFParser parser = new EBNFParser(new StringReader(text));
        try {
            parser.Unit();
            System.out.println("-- Parse succeeded.");
            System.out.println();
        } catch (ParseException e) {
            printText(text);
            System.out.println(e.getMessage());
            return;
            //e.printStackTrace();
        }
    }

    private static void one(String text) {
        printText(text);
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


        Rules.printAST(grammar);
        System.out.println("<><><><>");
//        Rules.printStructure(grammar);
//        System.out.println("<><><><>");
        Rules.printEBNF(grammar);
        System.out.println();
    }


    static void printText(String text) {
        System.out.println("==== Input");
        System.out.println("         1         2         3         4");
        System.out.println("1234567890123456789012345678901234567890");
        System.out.print(text);
        if ( ! text.endsWith("\n") )
            System.out.println();
    }
}

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

package org.seaborne.bnf.parser;

import java.io.PrintStream;
import java.util.List;

public record PrintFrame(PrintStream out, String labelFmt, String indent, int numRules) {

    PrintFrame inc() { return new PrintFrame(out, labelFmt, indent+Internal.indent, numRules); }

    /** Non-standard structure print of a list. */
    static void printListExpressions(PrintFrame pFrame, String typeName, List<Expression> list) {
        pFrame.out().print("(");
        pFrame.out().print(typeName);
        PrintFrame pFrame2 = pFrame.inc();
        list.forEach(elt -> {
            pFrame.out().print(" ");
            elt.printStructure(pFrame2);
        });
        pFrame.out().print(")");
    }

    // Modifier, non-standard form
    static void printModifierFunction(PrintFrame pFrame, Expression expr, String modifier) {
        pFrame.out().print("(");
        pFrame.out().print(modifier);
        pFrame.out().print(" ");
        expr.printStructure(pFrame);
        pFrame.out().print(")");
    }

    /** Print EBNF */
    static void printList(PrintFrame pFrame, String separator, List<Expression> list) {
        PrintFrame pFrame2 = pFrame.inc();
        boolean first = true;
        for ( Expression elt : list ) {
            if ( first )
                first = false;
            else
                pFrame.out().print(separator);
            printUnary(pFrame, elt);
        }
    }

    static void printModifierEBNF(PrintFrame pFrame, Expression expr, String modifier) {
        printUnary(pFrame, expr);
        pFrame.out().print(modifier);
    }

    private static void printUnary(PrintFrame pFrame, Expression expr) {
        boolean printParens = ! expr.printAtomic(pFrame);
        if ( printParens )
            pFrame.out().print("( ");
        expr.printBNF(pFrame);
        if ( printParens )
            pFrame.out().print(" )");
    }
}

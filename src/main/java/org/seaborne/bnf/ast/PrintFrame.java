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

package org.seaborne.bnf.ast;

import java.util.List;

import org.seaborne.bnf.sys.IndentedOutput;

public record PrintFrame(IndentedOutput out, String labelFmt, String indent, int numRules) {

    //PrintFrame inc() { return new PrintFrame(out, labelFmt, indent+Internal.indent, numRules); }

    /** Print a list of expressions as the AST structure. */
    static void printListAST(PrintFrame pFrame, String typeName, List<Expression> list) {
        pFrame.out().print("(");
        pFrame.out().print(typeName);
        list.forEach(elt -> {
            pFrame.out().print(" ");
            elt.printAST(pFrame);
        });
        pFrame.out().print(")");
    }

    /** Non-standard structure print of a list. */
    /** Print a list of expressions as an indented lisp-like structure. */
    static void printListStructure(PrintFrame pFrame, String typeName, List<Expression> list) {
        pFrame.out().print("(");
        pFrame.out().print(typeName);
        pFrame.out().println();
        pFrame.out().incIndent();
        list.forEach(elt -> {
            elt.printStructure(pFrame);
            pFrame.out().println();
        });
        pFrame.out().decIndent();
        pFrame.out().print(")");
    }

    // Modifier, non-standard form
    static void printModifierFunction(PrintFrame pFrame, Expression expr, String modifier) {
        pFrame.out().print("(");
        pFrame.out().print(modifier);
        pFrame.out().print(" ");
        expr.printAST(pFrame);
        pFrame.out().print(")");
    }

    // Expression, non-standard form
    static void printExpression1(PrintFrame pFrame, String name, Expression arg) {
        pFrame.out().print("(");
        pFrame.out().print(name);
        pFrame.out().incIndent();
        arg.printAST(pFrame);
        pFrame.out().decIndent();
        pFrame.out().print(")");
    }

    /** Print EBNF */
    static void printList(PrintFrame pFrame, String separator, List<Expression> list) {
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

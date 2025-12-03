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

import java.util.List;
import java.util.function.BiConsumer;

import org.seaborne.bnf.Grammar;
import org.seaborne.bnf.ast.Internal;
import org.seaborne.bnf.ast.PrintFrame;
import org.seaborne.bnf.ast.Rule;

public class RulesWriter {

    public static void printAST(Grammar grammar) {
        printAST(output(), grammar);
    }

    public static void printAST(IndentedOutput output, Grammar grammar) {
        System.out.println("---- AST:");
        printEachRule(output, grammar.rules(), (pFrame, rule)->rule.printAST(pFrame));
    }

    public static void printStructure(Grammar grammar) {
        printStructure(output(), grammar);
    }

    public static void printStructure(IndentedOutput output, Grammar grammar) {
        System.out.println("---- Structure:");
        printEachRule(output, grammar.rules(), (pFrame, rule)->rule.printStructure(pFrame));
    }

    public static void printEBNF(Grammar grammar) {
        printEBNF(output(), grammar);
    }

    public static void printEBNF(IndentedOutput output, Grammar grammar) {
        printEachRule(output, grammar.rules(), (pFrame, rule)->rule.printEBNF(pFrame));
    }

    private static void printEachRule(IndentedOutput output, List<Rule> rules, BiConsumer<PrintFrame, Rule> action) {
        String fmtField = calcLabelFmt(rules);
        PrintFrame pFrame = new PrintFrame(output, fmtField, Internal.firstIndent, rules.size());
        rules.forEach(rule -> {
            action.accept(pFrame, rule);
            pFrame.out().println();
        });
    }

    private static IndentedOutput output() {
        IndentedOutput iOut = new IndentedOutput(System.out);
        iOut.setUnitIndent(4);
        return iOut;
    }

    private static String calcLabelFmt(List<Rule> rules) {
        // Label width, excluding []
        int maxLabelWidth = -1;
        for ( Rule rule : rules ) {
            int w = length(rule.getLabel());
            maxLabelWidth = Math.max(maxLabelWidth, w);
        };
        //int digits = 1+pFrame.numRules()/10;

        //if ( no labels, use counter)

        int width = (maxLabelWidth == -1)
                ? -(1+rules.size()/10)+2    // Counting labels, including []
                : maxLabelWidth;
        width += 3; // For [] and a space
        String fmtField = "%-"+width+"s";
        return fmtField;
    }

    // Include surrounding []
    private static int length(String label) {
        return label==null ? Internal.dftLabel.length()-2 : label.length();
    }

}

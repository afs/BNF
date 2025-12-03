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

public class Alternatives extends Expression {
    public final List<Expression> alternatives;

    public static Expression create(List<Expression> exprs) {
        if ( exprs.size() == 1 )
            return exprs.getFirst();
        return new Alternatives(exprs);
    }

    private Alternatives(List<Expression> exprs) {
        alternatives = List.copyOf(exprs);
    }

    // Not top level alternative (which does not need parentheses).
    @Override
    public boolean printAtomic(PrintFrame pFrame) {
        return false;
    }

    @Override
    public void printAST(PrintFrame pFrame) {
        PrintFrame.printListAST(pFrame, "Alt", alternatives);
    }

    @Override
    public void printStructure(PrintFrame pFrame) {
        PrintFrame.printListStructure(pFrame, "alt", alternatives);
    }


    @Override
    public void printBNF(PrintFrame pFrame) {
        PrintFrame.printList(pFrame, " | ", alternatives);
    }
}

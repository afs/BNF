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

public class Sequence extends Expression {

    public static Expression create(List<Expression> exprs) {
        if ( exprs.size() == 1 )
            return exprs.getFirst();
        return new Sequence(exprs);
    }

    public final List<Expression> sequence;

    private Sequence(List<Expression> exprs) {
        sequence = List.copyOf(exprs);
    }

    @Override
    public boolean printAtomic(PrintFrame pFrame) {
        return true;
    }

    @Override
    public void printAST(PrintFrame pFrame) {
        PrintFrame.printListAST(pFrame, "Seq", sequence);
    }

    @Override
    public void printStructure(PrintFrame pFrame) {
        PrintFrame.printListStructure(pFrame, "seq", sequence);
    }

    @Override
    public void printBNF(PrintFrame pFrame) {
        PrintFrame.printList(pFrame, " ", sequence);
    }
}

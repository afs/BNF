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

import java.util.Objects;

// Unparsed character range, excluding the surounding [...]
public class CharRange extends Expression {

    private final String str;

    public CharRange(String str) {
        Objects.requireNonNull(str);
        this.str = str;
    }

    @Override
    public boolean printAtomic(PrintFrame pFrame) {
        return true;
    }

    @Override
    public void printAST(PrintFrame pFrame) {
        System.out.printf("[%s]", str);
    }

    @Override
    public void printStructure(PrintFrame pFrame) {
        printAST(pFrame);
        pFrame.out().println();
    }

    @Override
    public void printBNF(PrintFrame pFrame) {
        System.out.printf("[%s]", str);
    }
}

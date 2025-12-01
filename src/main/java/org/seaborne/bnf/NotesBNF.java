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

public class NotesBNF {

// // Source - https://stackoverflow.com/a/52727410
// // Posted by Theodore Norvell
// // Retrieved 2025-12-01, License - CC BY-SA 4.0
//
// TOKEN : { < BLOCK_COMMENT_START : "/*" >  : WITHIN_BLOCK_COMMENT }
// <WITHIN_BLOCK_COMMENT> TOKEN: { <CHAR_IN_COMMENT: ~[] > }
// <WITHIN_BLOCK_COMMENT> TOKEN: { < END_BLOCK_COMMENT: "*/" > : DEFAULT }
//
// SKIP : {
//   "\n" | " "
// }
//
////Source - https://stackoverflow.com/a/52727410
////Posted by Theodore Norvell
////Retrieved 2025-12-01, License - CC BY-SA 4.0
//
//void start() : {String s ; } {
//  (
//      s = comment()  {System.out.println(s); }
//  )*
//}
//
//String comment() :
//{   Token t ;
//  StringBuffer b = new StringBuffer() ;
//}
//{  <START_BLOCK_COMMENT>
// (
//       t=<CHAR_IN_COMMENT>  {b.append( t.image ) ; }
// )*
// <END_BLOCK_COMMENT>
// {return b.toString() ; }
//}



    /* XML https://www.w3.org/TR/xml/#sec-notation
        #xN
        where N is a hexadecimal integer, the expression matches the character whose number (code point) in ISO/IEC 10646 is N. The number of leading zeros in the #xN form is insignificant.

        [a-zA-Z], [#xN-#xN]
        matches any Char with a value in the range(s) indicated (inclusive).

        [abc], [#xN#xN#xN]
        matches any Char with a value among the characters enumerated. Enumerations and ranges can be mixed in one set of brackets.

        [^a-z], [^#xN-#xN]
        matches any Char with a value outside the range indicated.

        [^abc], [^#xN#xN#xN]
        matches any Char with a value not among the characters given. Enumerations and ranges of forbidden values can be mixed in one set of brackets.

        "string"
        matches a literal string matching that given inside the double quotes.

        'string'
        matches a literal string matching that given inside the single quotes.

        These symbols may be combined to match more complex patterns as follows, where A and B represent simple expressions:

        (expression)
        expression is treated as a unit and may be combined as described in this list.

        A?
        matches A or nothing; optional A.

        A B
        matches A followed by B. This operator has higher precedence than alternation; thus A B | C D is identical to (A B) | (C D).

        A | B
        matches A or B.

        A - B
        matches any string that matches A but does not match B.

        A+
        matches one or more occurrences of A. Concatenation has higher precedence than alternation; thus A+ | B+ is identical to (A+) | (B+).

        A*
        matches zero or more occurrences of A. Concatenation has higher precedence than alternation; thus A* | B* is identical to (A*) | (B*).

        Other notations used in the productions are:

        /* ... * /
        comment.

        [ wfc: ... ]
        well-formedness constraint; this identifies by name a constraint on well-formed documents associated with a production.

        [ vc: ... ]
        validity constraint; this identifies by name a constraint on valid documents associated with a production.
        */
}

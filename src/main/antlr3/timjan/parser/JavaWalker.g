/**
 * For more information see the head comment within the 'java.g' grammar file
 * that defines the input for this tree grammar.
 *
 * BSD licence
 * 
 * Copyright (c) 2007-2008 by HABELITZ Software Developments
 *
 * All rights reserved.
 * 
 * http://www.habelitz.com
 *
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY HABELITZ SOFTWARE DEVELOPMENTS ('HSD') ``AS IS'' 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL 'HSD' BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
tree grammar JavaWalker;

options {
    ASTLabelType = CommonTree;
    backtrack = true;
    memoize = true;
    tokenVocab = Java;
}

@header {
    package timjan.parser;
    import timjan.syntax.*;
    import timjan.util.*;
    import java.util.ArrayList;
    import java.util.LinkedList;
    import java.util.Map;
    import java.util.HashMap;
}


// Starting point for parsing a Java file.
javaSource returns [JavaSource cf]
@init { 
    List<ImportStatement> ids = new ArrayList<ImportStatement>(); 
    List<Object> cds = new ArrayList<Object>();
}
    :   ^(JAVA_SOURCE annotationList 
            pd=packageDeclaration? 
            (id=importDeclaration { ids.add(id); })* 
            (cd=typeDeclaration { cds.add(cd); })*
            )
        { $cf = new JavaSource(pd, ids, cds); }
    ;

packageDeclaration returns [PackageStatement ps]
    :   ^(PACKAGE qi=qualifiedIdentifier)  
        { $ps = new PackageStatement(qi); }
    ;
    
importDeclaration returns [ImportStatement is]
    :   ^(IMPORT st=STATIC? qi=qualifiedIdentifier ds=DOTSTAR?)
        { $is = new ImportStatement(qi, st != null, ds != null); }
    ;
    
typeDeclaration returns [Object td]
    :   ^(CLASS ml=modifierList i=IDENT genericTypeParameterList? ec=extendsClause? ic=implementsClause? tls=classTopLevelScope)
        { $td = new ClassDefinition(ml, $i.text, ec, ic, tls); }
    |   ^(INTERFACE modifierList IDENT genericTypeParameterList? extendsClause? interfaceTopLevelScope)
    |   ^(ENUM modifierList IDENT implementsClause? enumTopLevelScope)
    |   ^(AT modifierList IDENT annotationTopLevelScope)
    ;

extendsClause returns [List<ClassReference> ts]
@init { ts = new LinkedList<ClassReference>(); }
// actually 'type' for classes and 'type+' for interfaces, but this has 
// been resolved by the parser grammar.
    :   ^(EXTENDS_CLAUSE (t=type { $ts.add((ClassReference)t); })+)
    ;   
    
implementsClause returns [List<ClassReference> ts]
@init { ts = new LinkedList<ClassReference>(); }
    :   ^(IMPLEMENTS_CLAUSE (t=type { $ts.add((ClassReference)t); })+)
    ;
        
genericTypeParameterList
    :   ^(GENERIC_TYPE_PARAM_LIST genericTypeParameter+)
    ;

genericTypeParameter
    :   ^(IDENT bound?)
    ;
        
bound
    :   ^(EXTENDS_BOUND_LIST type+)
    ;

enumTopLevelScope
    :   ^(ENUM_TOP_LEVEL_SCOPE enumConstant+ classTopLevelScope?)
    ;
    
enumConstant
    :   ^(IDENT annotationList arguments? classTopLevelScope?)
    ;
    
    
classTopLevelScope returns [List<AbstractMember> ams]
@init { $ams = new LinkedList<AbstractMember>(); }
    :   ^(CLASS_TOP_LEVEL_SCOPE (cd=classScopeDeclarations { $ams.add(cd); })*)
    ;
    
classScopeDeclarations returns [AbstractMember am]
    :   ^(CLASS_INSTANCE_INITIALIZER block)
    |   ^(CLASS_STATIC_INITIALIZER block)
    |   ^(FUNCTION_METHOD_DECL ml=modifierList genericTypeParameterList? rt=type i=IDENT pl=formalParameterList arrayDeclaratorList? tc=throwsClause? block?)
        { $am = new MethodDeclaration(ml, rt, $i.text, pl, tc); }
    |   ^(VOID_METHOD_DECL ml=modifierList genericTypeParameterList? i=IDENT pl=formalParameterList tc=throwsClause? block?)
        { $am = new MethodDeclaration(ml, null, $i.text, pl, tc); }
    |   ^(VAR_DECLARATION modifierList type variableDeclaratorList)
    |   ^(CONSTRUCTOR_DECL modifierList genericTypeParameterList? formalParameterList throwsClause? block)
    |   typeDeclaration
    ;
    
interfaceTopLevelScope
    :   ^(INTERFACE_TOP_LEVEL_SCOPE interfaceScopeDeclarations*)
    ;
    
interfaceScopeDeclarations
    :   ^(FUNCTION_METHOD_DECL modifierList genericTypeParameterList? type IDENT formalParameterList arrayDeclaratorList? throwsClause?)
    |   ^(VOID_METHOD_DECL modifierList genericTypeParameterList? IDENT formalParameterList throwsClause?)
                         // Interface constant declarations have been switched to variable
                         // declarations by 'java.g'; the parser has already checked that
                         // there's an obligatory initializer.
    |   ^(VAR_DECLARATION modifierList type variableDeclaratorList)
    |   typeDeclaration
    ;

variableDeclaratorList
    :   ^(VAR_DECLARATOR_LIST variableDeclarator+)
    ;

variableDeclarator
    :   ^(VAR_DECLARATOR variableDeclaratorId variableInitializer?)
    ;
    
variableDeclaratorId returns [String s]
    :   ^(IDENT arrayDeclaratorList?) { $s = $IDENT.text; }
    ;

variableInitializer
    :   arrayInitializer
    |   expression
    ;

arrayDeclarator
    :   LBRACK RBRACK
    ;

arrayDeclaratorList
    :   ^(ARRAY_DECLARATOR_LIST ARRAY_DECLARATOR*)  
    ;
    
arrayInitializer
    :   ^(ARRAY_INITIALIZER variableInitializer*)
    ;

throwsClause returns [List<QualifiedIdentifier> qis]
@init { $qis = new LinkedList<QualifiedIdentifier>(); }
    :   ^(THROWS_CLAUSE (qi=qualifiedIdentifier { $qis.add(qi); })+)
    ;

modifierList returns [List<Modifier> ms]
@init { $ms = new LinkedList<Modifier>(); }
    :   ^(MODIFIER_LIST (m=modifier { $ms.add(m); })*)
    ;

modifier returns [Modifier mod]
    :   m=(PUBLIC | PROTECTED | PRIVATE | STATIC | ABSTRACT | NATIVE | SYNCHRONIZED | TRANSIENT | VOLATILE | STRICTFP) 
    { $mod = SyntaxUtil.getModifier($m.text); }
    |   lm=localModifier { $mod = lm; }
    ;

localModifierList returns [List<Modifier> ms]
@init { $ms = new LinkedList<Modifier>(); }
    :   ^(LOCAL_MODIFIER_LIST (m=localModifier { $ms.add(m); })*)
    ;

localModifier returns [Modifier mod]
    :   FINAL
    |   a=annotation { $mod = a; }
    ;

type returns [Type t]
    :   ^(TYPE ((pt=primitiveType { $t = PrimitiveTypes.valueOf($pt.text.toUpperCase()); }) | (qt=qualifiedTypeIdent { $t = qt; })) arrayDeclaratorList?)
    ;

qualifiedTypeIdent returns [Type t]
    :   ^(QUALIFIED_TYPE_IDENT i=typeIdent+) 
        { $t = i; }
    ;

typeIdent returns [Type t]
    :   ^(i=IDENT ta=genericTypeArgumentList?)
        { $t = new ClassReference($i.text, ta); }
    ;

primitiveType
    :   BOOLEAN
    |   CHAR
    |   BYTE
    |   SHORT
    |   INT
    |   LONG
    |   FLOAT
    |   DOUBLE
    ;

genericTypeArgumentList returns [List<TypeArgument> tas]
@init { $tas = new LinkedList<TypeArgument>(); }
    :   ^(GENERIC_TYPE_ARG_LIST (ta=genericTypeArgument { $tas.add(ta); }) +)
    ;
    
genericTypeArgument returns [TypeArgument ta]
    :   t=type { $ta = new TypeArgument(t, null, null); }
    |   ^(QUESTION genericWildcardBoundType?)
    ;

genericWildcardBoundType returns [TypeArgument ta]
    :   ^(EXTENDS t=type ) { $ta = new TypeArgument(null, t, null); }|
    |   ^(SUPER t=type) { $ta = new TypeArgument(null, null, t); }
    ;

formalParameterList returns [List<MethodArgument> args] 
@init { $args = new LinkedList<MethodArgument>(); }
    :   ^(FORMAL_PARAM_LIST (sd=formalParameterStandardDecl { $args.add(sd); })* (va=formalParameterVarargDecl { $args.add(va); })?) 
    ;
    
formalParameterStandardDecl returns [MethodArgument arg]
    :   ^(FORMAL_PARAM_STD_DECL ml=localModifierList t=type i=variableDeclaratorId) { $arg = new MethodArgument(i, t, ml, false); }
    ;
    
formalParameterVarargDecl returns [MethodArgument arg]
    :   ^(FORMAL_PARAM_VARARG_DECL ml=localModifierList t=type i=variableDeclaratorId) { $arg = new MethodArgument(i, t, ml, true); }
    ;
    
qualifiedIdentifier returns [QualifiedIdentifier qi]
    : s=qualifiedIdentifierParts { $qi = new QualifiedIdentifier(s); }
    ;

qualifiedIdentifierParts returns [List<String> s]
    :   IDENT { $s = new LinkedList<String>(); $s.add($IDENT.text); }
    |   ^(DOT qi=qualifiedIdentifierParts IDENT) { $s = qi; $s.add($IDENT.text); }
    ;
    
// ANNOTATIONS

annotationList
    :   ^(ANNOTATION_LIST annotation*)
    ;

annotation returns [AnnotationStatement as]
    :   ^(AT qi=qualifiedIdentifier ai=annotationInit?)
    { $as = new AnnotationStatement(qi, ai); }
    ;
    
annotationInit returns [Map<String, AnnotationInitializer> ais]
    :   ^(ANNOTATION_INIT_BLOCK ai=annotationInitializers { $ais = ai; })
    ;

annotationInitializers returns [Map<String, AnnotationInitializer> ais]
@init { $ais = new HashMap<String, AnnotationInitializer>(); }
    :   ^(ANNOTATION_INIT_KEY_LIST (ai=annotationInitializer { $ais.put($ai.name, $ai.init); })+)
    |   ^(ANNOTATION_INIT_DEFAULT_KEY ae=annotationElementValue { $ais.put("default", ae); })
    ;
    
annotationInitializer returns [String name, AnnotationInitializer init]
    :   ^(i=IDENT ae=annotationElementValue) { $name = $i.text; $init = ae; }
    ;
    
annotationElementValue returns [AnnotationInitializer ai]
@init { List<AnnotationInitializer> ais = new LinkedList<AnnotationInitializer>(); }
    :   ^(ANNOTATION_INIT_ARRAY_ELEMENT (ae=annotationElementValue { ais.add(ae); })*) { $ai = new AnnotationInitializer(ais); }
    |   an=annotation { $ai=new AnnotationInitializer(an); }
    |   ex=expression { $ai=new AnnotationInitializer($ex.text); }
    ;
    
annotationTopLevelScope
    :   ^(ANNOTATION_TOP_LEVEL_SCOPE annotationScopeDeclarations*)
    ;
    
annotationScopeDeclarations
    :   ^(ANNOTATION_METHOD_DECL modifierList type IDENT annotationDefaultValue?)
    |   ^(VAR_DECLARATION modifierList type variableDeclaratorList)
    |   typeDeclaration
    ;
    
annotationDefaultValue
    :   ^(DEFAULT annotationElementValue)
    ;

// STATEMENTS / BLOCKS

block
    :   ^(BLOCK_SCOPE blockStatement*)
    ;
    
blockStatement
    :   localVariableDeclaration
    |   typeDeclaration
    |   statement
    ;
    
localVariableDeclaration
    :   ^(VAR_DECLARATION localModifierList type variableDeclaratorList)
    ;
    
        
statement
    :   block
    |   ^(ASSERT expression expression?)
    |   ^(IF parenthesizedExpression statement statement?)
    |   ^(FOR forInit forCondition forUpdater statement)
    |   ^(FOR_EACH localModifierList type IDENT expression statement) 
    |   ^(WHILE parenthesizedExpression statement)
    |   ^(DO statement parenthesizedExpression)
    |   ^(TRY block catches? block?)  // The second optional block is the optional finally block.
    |   ^(SWITCH parenthesizedExpression switchBlockLabels)
    |   ^(SYNCHRONIZED parenthesizedExpression block)
    |   ^(RETURN expression?)
    |   ^(THROW expression)
    |   ^(BREAK IDENT?)
    |   ^(CONTINUE IDENT?)
    |   ^(LABELED_STATEMENT IDENT statement)
    |   expression
    |   SEMI // Empty statement.
    ;
        
catches
    :   ^(CATCH_CLAUSE_LIST catchClause+)
    ;
    
catchClause
    :   ^(CATCH formalParameterStandardDecl block)
    ;

switchBlockLabels
    :   ^(SWITCH_BLOCK_LABEL_LIST switchCaseLabel* switchDefaultLabel? switchCaseLabel*)
    ;
        
switchCaseLabel
    :   ^(CASE expression blockStatement*)
    ;
    
switchDefaultLabel
    :   ^(DEFAULT blockStatement*)
    ;
    
forInit
    :   ^(FOR_INIT (localVariableDeclaration | expression*)?)
    ;
    
forCondition
    :   ^(FOR_CONDITION expression?)
    ;
    
forUpdater
    :   ^(FOR_UPDATE expression*)
    ;
    
// EXPRESSIONS

parenthesizedExpression
    :   ^(PARENTESIZED_EXPR expression)
    ;
    
expression
    :   ^(EXPR expr)
    ;

expr
    :   ^(ASSIGN expr expr)
    |   ^(PLUS_ASSIGN expr expr)
    |   ^(MINUS_ASSIGN expr expr)
    |   ^(STAR_ASSIGN expr expr)
    |   ^(DIV_ASSIGN expr expr)
    |   ^(AND_ASSIGN expr expr)
    |   ^(OR_ASSIGN expr expr)
    |   ^(XOR_ASSIGN expr expr)
    |   ^(MOD_ASSIGN expr expr)
    |   ^(BIT_SHIFT_RIGHT_ASSIGN expr expr)
    |   ^(SHIFT_RIGHT_ASSIGN expr expr)
    |   ^(SHIFT_LEFT_ASSIGN expr expr)
    |   ^(QUESTION expr expr expr)
    |   ^(LOGICAL_OR expr expr)
    |   ^(LOGICAL_AND expr expr)
    |   ^(OR expr expr)
    |   ^(XOR expr expr)
    |   ^(AND expr expr)
    |   ^(EQUAL expr expr)
    |   ^(NOT_EQUAL expr expr)
    |   ^(INSTANCEOF expr type)
    |   ^(LESS_OR_EQUAL expr expr)
    |   ^(GREATER_OR_EQUAL expr expr)
    |   ^(BIT_SHIFT_RIGHT expr expr)
    |   ^(SHIFT_RIGHT expr expr)
    |   ^(GREATER_THAN expr expr)
    |   ^(SHIFT_LEFT expr expr)
    |   ^(LESS_THAN expr expr)
    |   ^(PLUS expr expr)
    |   ^(MINUS expr expr)
    |   ^(STAR expr expr)
    |   ^(DIV expr expr)
    |   ^(MOD expr expr)
    |   ^(UNARY_PLUS expr)
    |   ^(UNARY_MINUS expr)
    |   ^(PRE_INC expr)
    |   ^(PRE_DEC expr)
    |   ^(POST_INC expr)
    |   ^(POST_DEC expr)
    |   ^(NOT expr)
    |   ^(LOGICAL_NOT expr)
    |   ^(CAST_EXPR type expr)
    |   primaryExpression
    ;
    
primaryExpression
    :   ^(  DOT
            (   primaryExpression
                (   IDENT
                |   THIS
                |   SUPER
                |   innerNewExpression
                |   CLASS
                )
            |   primitiveType CLASS
            |   VOID CLASS
            )
        )
    |   parenthesizedExpression
    |   IDENT
    |   ^(METHOD_CALL primaryExpression genericTypeArgumentList? arguments)
    |   explicitConstructorCall
    |   ^(ARRAY_ELEMENT_ACCESS primaryExpression expression)
    |   literal
    |   newExpression
    |   THIS
    |   arrayTypeDeclarator
    |   SUPER
    ;
    
explicitConstructorCall
    :   ^(THIS_CONSTRUCTOR_CALL genericTypeArgumentList? arguments)
    |   ^(SUPER_CONSTRUCTOR_CALL primaryExpression? genericTypeArgumentList? arguments)
    ;

arrayTypeDeclarator
    :   ^(ARRAY_DECLARATOR (arrayTypeDeclarator | qualifiedIdentifier | primitiveType))
    ;

newExpression
    :   ^(  STATIC_ARRAY_CREATOR
            (   primitiveType newArrayConstruction
            |   genericTypeArgumentList? qualifiedTypeIdent newArrayConstruction
            )
        )
    |   ^(CLASS_CONSTRUCTOR_CALL genericTypeArgumentList? qualifiedTypeIdent arguments classTopLevelScope?)
    ;

innerNewExpression // something like 'InnerType innerType = outer.new InnerType();'
    :   ^(CLASS_CONSTRUCTOR_CALL genericTypeArgumentList? IDENT arguments classTopLevelScope?)
    ;
    
newArrayConstruction
    :   arrayDeclaratorList arrayInitializer
    |   expression+ arrayDeclaratorList?
    ;

arguments
    :   ^(ARGUMENT_LIST expression*)
    ;

literal 
    :   HEX_LITERAL
    |   OCTAL_LITERAL
    |   DECIMAL_LITERAL
    |   FLOATING_POINT_LITERAL
    |   CHARACTER_LITERAL
    |   STRING_LITERAL
    |   TRUE
    |   FALSE
    |   NULL
    ;

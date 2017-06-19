/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.backend.jvm.codegen

import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.codegen.*
import org.jetbrains.kotlin.codegen.inline.*
import org.jetbrains.kotlin.codegen.state.GenerationState
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.incremental.components.LookupLocation
import org.jetbrains.kotlin.incremental.components.NoLookupLocation
import org.jetbrains.kotlin.ir.expressions.IrBlock
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Type
import org.jetbrains.org.objectweb.asm.commons.Method
import org.jetbrains.org.objectweb.asm.tree.MethodNode

interface IrCallGenerator {


    fun genCallInner(callableMethod: Callable, callDefault: Boolean, codegen: ExpressionCodegen) {
        if (!callDefault) {
            callableMethod.genInvokeInstruction(codegen.mv)
        } else {
            (callableMethod as CallableMethod).genInvokeDefaultInstruction(codegen.mv)
        }
    }


    fun genValueAndPut(
            valueParameterDescriptor: ValueParameterDescriptor?,
            argumentExpression: IrExpression,
            parameterType: Type,
            parameterIndex: Int,
            codegen: ExpressionCodegen,
            blockInfo: BlockInfo) {
        codegen.gen(argumentExpression, parameterType, blockInfo)
    }

    fun putValueIfNeeded(parameterType: Type, value: StackValue, kind: ValueKind, parameterIndex: Int, codegen: ExpressionCodegen) {
        value.put(parameterType, codegen.visitor)
    }

    object DefaultCallGenerator: IrCallGenerator {

    }
}

class IrSourceCompilerForInline(
        override val state: GenerationState,
        override val callElement: IrMemberAccessExpression
        ): SourceCompilerForInline {


    //TODO
    override val lookupLocation: LookupLocation
        get() = NoLookupLocation.FROM_BACKEND

    //TODO
    override val callElementText: String
        get() = callElement.toString()

    override val callsiteFile: PsiFile?
        get() = TODO("not implemented")

    override val contextKind: OwnerKind
        get() = OwnerKind.getMemberOwnerKind(callElement.descriptor.containingDeclaration)

    override val inlineCallSiteInfo: InlineCallSiteInfo
        get() = InlineCallSiteInfo("TODO", null, null)

    override val lazySourceMapper: DefaultSourceMapper
        get() = DefaultSourceMapper(SourceInfo("TODO", "TODO", 100))

    override fun generateLambdaBody(adapter: MethodVisitor, jvmMethodSignature: JvmMethodSignature, lambdaInfo: ExpressionLambda): SMAP {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doCreateMethodNodeFromSource(callableDescriptor: FunctionDescriptor, jvmSignature: JvmMethodSignature, callDefault: Boolean, asmMethod: Method): SMAPAndMethodNode {
        TODO("not implemented")
    }

    override fun generateAndInsertFinallyBlocks(intoNode: MethodNode, insertPoints: List<MethodInliner.PointForExternalFinallyBlocks>, offsetForFinallyLocalVar: Int) {
        //TODO("not implemented")
    }

    override fun isCallInsideSameModuleAsDeclared(functionDescriptor: FunctionDescriptor): Boolean {
        //TODO("not implemented")
        return true
    }

    override fun isFinallyMarkerRequired(): Boolean {
        //TODO("not implemented")
        return false
    }

    override val compilationContextDescriptor: DeclarationDescriptor
        get() = callElement.descriptor

    override val compilationContextFunctionDescriptor: FunctionDescriptor
        get() = callElement.descriptor as FunctionDescriptor

    override fun getContextLabels(): Set<String> {
        //TODO
        return emptySet()
    }

    override fun initializeInlineFunctionContext(functionDescriptor: FunctionDescriptor) {
        //TODO
    }
}

class IrInlineCodegen(
        codegen: BaseExpressionCodegen,
        state: GenerationState,
        function: FunctionDescriptor,
        typeParameterMappings: TypeParameterMappings,
        sourceCompiler: SourceCompilerForInline
) : InlineCodegen(codegen, state, function, typeParameterMappings, sourceCompiler), IrCallGenerator {

    override fun putClosureParametersOnStack(next: LambdaInfo, functionReferenceReceiver: StackValue?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun genValueAndPut(valueParameterDescriptor: ValueParameterDescriptor?, argumentExpression: IrExpression, parameterType: Type, parameterIndex: Int, codegen: ExpressionCodegen, blockInfo: BlockInfo) {
        if (argumentExpression is IrBlock && argumentExpression.origin == IrStatementOrigin.LAMBDA) {
            //TODO
        }
        super.genValueAndPut(valueParameterDescriptor, argumentExpression, parameterType, parameterIndex, codegen, blockInfo)
    }
}
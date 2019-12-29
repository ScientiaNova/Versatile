package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.velisp.*
import com.scientianovateam.versatile.velisp.functions.arithemetic.*
import com.scientianovateam.versatile.velisp.functions.constructor.*
import com.scientianovateam.versatile.velisp.functions.lists.*
import com.scientianovateam.versatile.velisp.functions.logic.*
import com.scientianovateam.versatile.velisp.functions.mics.IsFunction
import com.scientianovateam.versatile.velisp.functions.mics.TypeOfFunction
import com.scientianovateam.versatile.velisp.functions.optional.*
import com.scientianovateam.versatile.velisp.functions.string.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID)
object VELISPEventSubscriber {
    @SubscribeEvent
    fun register(e: VersatileRegistryEvent) {
        VELISP_TYPES["any"] = { AnyType }
        VELISP_TYPES["number"] = { NumberType }
        VELISP_TYPES["bool"] = { BoolType }
        VELISP_TYPES["string"] = { StringType }
        VELISP_TYPES["list"] = {
            val (name, info) = VELISPType.separateNameAndInfo(it)
            ListType(VELISP_TYPES[name.toResLocV()]?.invoke(info)
                    ?: error("Didn't properly specify type for list type"))
        }
        VELISP_TYPES["optional"] = {
            val (name, info) = VELISPType.separateNameAndInfo(it)
            OptionalType(VELISP_TYPES[name.toResLocV()]?.invoke(info)
                    ?: error("Didn't properly specify type for optional type"))
        }
        VELISP_TYPES["function"] = {
            FunctionType(it.toIntOrNull() ?: error("Didn't specify amount of inputs for function type"))
        }

        VELISP_FUNCTIONS["+"] = AddFunction
        VELISP_FUNCTIONS["-"] = SubtractFunction
        VELISP_FUNCTIONS["*"] = MultiplyFunction
        VELISP_FUNCTIONS["/"] = DivideFunction
        VELISP_FUNCTIONS["//"] = FloorDivideFunction
        VELISP_FUNCTIONS["%"] = RemainderFunction
        VELISP_FUNCTIONS["**"] = PowerFunction
        VELISP_FUNCTIONS["="] = EqualFunction
        VELISP_FUNCTIONS["!="] = NotEqualFunction
        VELISP_FUNCTIONS["/="] = NotEqualFunction
        VELISP_FUNCTIONS[">"] = GreaterFunction
        VELISP_FUNCTIONS[">="] = GreaterOrEqualFunction
        VELISP_FUNCTIONS["<"] = LessFunction
        VELISP_FUNCTIONS["<="] = LessOrEqualFunction

        VELISP_FUNCTIONS.registerAll(
                //Arithmetic Functions
                AbsoluteFunction,
                AcosFunction,
                AcoshFunction,
                AddFunction,
                AsinFunction,
                AsinhFunction,
                AtanFunction,
                AtanhFunction,
                CeilFunction,
                CosFunction,
                CoshFunction,
                DivideFunction,
                ExpFunction,
                FloorDivideFunction,
                FloorFunction,
                LogFunction,
                MaxFunction,
                MinFunction,
                MultiplyFunction,
                PowerFunction,
                RemainderFunction,
                RootFunction,
                RoundFunction,
                RoundFunction,
                SinFunction,
                SinhFunction,
                SubtractFunction,
                TanFunction,
                TanhFunction,
                //Constructors
                CopyFunction,
                FormFunction,
                LambdaFunction,
                ListFunction,
                MaterialFunction,
                RangeFunction,
                SomeFunction,
                //List Function
                AllFunction,
                AnyFunction,
                AppendFunction,
                AverageFunction,
                ChunkedFunction,
                DistinctFunction,
                DropFunction,
                DropLastFunction,
                DropLastWhileFunction,
                DropWhileFunction,
                FilterFunction,
                FilterIndexedFunction,
                FilterIsInstanceFunction,
                FilterNotFunction,
                FlatMapFunction,
                FlattenFunction,
                FoldFunction,
                FoldIndexedFunction,
                IntersectFunction,
                IsEmptyFunction,
                IsNotEmptyFunction,
                MapFunction,
                MapIndexedFunction,
                MaxElementByFunction,
                MaxElementFunction,
                MinElementByFunction,
                MinElementFunction,
                NoneFunction,
                NthFunction,
                NthOrFunction,
                NthUncheckedFunction,
                RandomElementFunction,
                ReduceFunction,
                ReduceIndexedFunction,
                SizeFunction,
                SliceFunction,
                SumByFunction,
                SumFunction,
                TakeFunction,
                TakeLastFunction,
                TakeLastWhileFunction,
                TakeWhileFunction,
                UnionFunction,
                ZipFunction,
                //Logic Function
                AndFunction,
                EqualFunction,
                GreaterFunction,
                GreaterOrEqualFunction,
                IfFunction,
                InRangeFunction,
                LessFunction,
                LessOrEqualFunction,
                MatchFunction,
                NotEqualFunction,
                NotFunction,
                OrFunction,
                //Misc Functions
                IsFunction,
                TypeOfFunction,
                //Optional Function
                ExpectFunction,
                IsPresentFunction,
                MapOptionalFunction,
                UnwrapFunction,
                UnwrapOrFunction,
                //String Functions
                ConcatFunction,
                EndsWithFunction,
                RepeatFunction,
                ReplaceFunction,
                StartsWithFunction,
                StringContainsFunction,
                SubstringFunction
        )
    }
}
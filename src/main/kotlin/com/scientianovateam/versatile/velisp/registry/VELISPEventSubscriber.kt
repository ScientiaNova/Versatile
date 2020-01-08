package com.scientianovateam.versatile.velisp.registry

import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.common.registry.VersatileRegistryEvent
import com.scientianovateam.versatile.velisp.functions.arithemetic.*
import com.scientianovateam.versatile.velisp.functions.constructor.*
import com.scientianovateam.versatile.velisp.functions.lists.*
import com.scientianovateam.versatile.velisp.functions.logic.*
import com.scientianovateam.versatile.velisp.functions.mics.IsFunction
import com.scientianovateam.versatile.velisp.functions.mics.TypeOfFunction
import com.scientianovateam.versatile.velisp.functions.optional.*
import com.scientianovateam.versatile.velisp.functions.string.*
import com.scientianovateam.versatile.velisp.types.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object VELISPEventSubscriber {
    @SubscribeEvent
    fun register(e: VersatileRegistryEvent) {
        VELISP_TYPES["any"] = AnyType
        VELISP_TYPES["number"] = NumberType
        VELISP_TYPES["bool"] = BoolType
        VELISP_TYPES["string"] = StringType
        VELISP_TYPES["list"] = ListType
        VELISP_TYPES["optional"] = OptionalType
        VELISP_TYPES["function"] = FunctionType
        VELISP_TYPES["material"] = MaterialType
        VELISP_TYPES["form"] = FormType

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



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Tester {

    private static void testing(Instrument<Ingredient> m, Ingredient ingredient) {
        System.out.println("Before shredding:" + ingredient.type);
        m.process(ingredient);
        System.out.println("After shredding:" + ingredient.type);
    }

    private static void method(Ingredient ingredient) {
        System.out.println(ingredient.getName());
        ingredient.type = Type.SHREDDED;
    }

    private static void process(Supplier<List<Ingredient>> ingredients, Predicate<Ingredient> predicate) {
        List<Ingredient> test = ingredients.get();
        System.out.println("Fried ingredients");
        Consumer<Ingredient> printIngredientChangeTypeFrier = ingredient -> {
            System.out.print(ingredient.getName() + " ");
            ingredient.type = Type.FRIED;
        };
        test.stream().filter(predicate).forEach(printIngredientChangeTypeFrier);
        System.out.println("");
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static void main(String[] args) {
        Ingredient meat = new Ingredient("Meat");
        Ingredient rice = new Ingredient("Rice");
        Ingredient bread = new Ingredient("Bread");
        Instrument<Ingredient> shredderAnonymous = new Instrument<>() {
            @Override
            public void process(Ingredient ingredient) {
                System.out.println(ingredient.getName());
                ingredient.type = Type.SHREDDED;
            }
        };
        Instrument<Ingredient> shredderLambda = (i) -> {
            System.out.println(i.getName());
            i.type = Type.SHREDDED;
        };
        Instrument<Ingredient> shredderMethodReference = Tester::method;
        System.out.println("Anonymous class");
        testing(shredderAnonymous, meat);
        System.out.println("Lambda expression");
        testing(shredderLambda, rice);
        System.out.println("Method Reference");
        testing(shredderMethodReference, bread);

        System.out.println("\n#########Here begins SmartInstrument#########\n");
        Ingredient chicken = new Ingredient("Chicken");
        Ingredient egg = new Ingredient("Egg");
        Ingredient tomato = new Ingredient("Tomato");
        Supplier<List<Ingredient>> supplierBoil = () -> new ArrayList<>(List.of(chicken, egg, tomato, meat, rice));
        Supplier<List<Ingredient>> supplierFry = () -> new ArrayList<>(List.of(chicken, egg, tomato, meat, bread));
        Predicate<Ingredient> predicateBoil = ingredient -> ingredient.type == Type.SHREDDED || ingredient.type == Type.RAW;
        Predicate<Ingredient> predicateFry = ingredient -> ingredient.type == Type.SHREDDED;
        Consumer<Ingredient> printIngredientChangeTypeBoiler = ingredient -> {
            System.out.print(ingredient.getName() + " ");
            ingredient.type = Type.BOILED;
        };
        SmartInstrument boiler = (list, predicate) -> {
            System.out.println("Boiled ingredients");
            list.get().stream().filter(predicate).forEach(printIngredientChangeTypeBoiler);
            System.out.println("");
        };
        boiler.process(supplierBoil, predicateBoil);
        System.out.println("After boiling:");
        System.out.println("Egg's type:" + egg.type);
        System.out.println("Meat's type:" + meat.type);
        System.out.println("Rice's type:" + rice.type);
        SmartInstrument frier = Tester::process;
        frier.process(supplierFry, predicateFry);
        System.out.println("After frying:");
        System.out.println("Egg's type:" + egg.type);
        System.out.println("Bread's type:" + bread.type);
    }
}

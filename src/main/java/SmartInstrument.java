

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SmartInstrument {
    public void process(Supplier<List<Ingredient>> ingredients, Predicate<Ingredient> predicate);
}

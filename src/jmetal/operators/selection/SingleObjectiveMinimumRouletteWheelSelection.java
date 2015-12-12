package jmetal.operators.selection;

import java.util.List;

import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.impl.MersenneTwisterGenerator;

public class SingleObjectiveMinimumRouletteWheelSelection<S extends Solution<?>>
		implements SelectionOperator<List<S>, S> {

	private MersenneTwisterGenerator random;

	public SingleObjectiveMinimumRouletteWheelSelection() {
		random = new MersenneTwisterGenerator();
	}

	@Override
	public S execute(List<S> source) {
		int sum = 0;
		double[] percentages = new double[source.size()];

		for (S solution : source) {
			sum += solution.getObjective(0);
		}
		
		// Gera percentagens cumulativas
		for (int i = 0; i < percentages.length; i++) {
			percentages[i] = sum / source.get(i).getObjective(0); // (1 / (source[i].objective/sum))
			if(i > 0){
				percentages[i] += percentages[i-1];
			}
		}
		
		double coin = random.nextDouble(); // joga a moeda
		
		for (int i = 0; i < percentages.length; i++) {
			if(percentages[i] >= coin){
				return source.get(i);
			}
		}
						
		return source.get(random.nextInt(0, source.size()-1));
	}

}

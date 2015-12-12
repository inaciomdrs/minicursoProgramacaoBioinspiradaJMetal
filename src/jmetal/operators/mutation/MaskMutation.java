package jmetal.operators.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.impl.MersenneTwisterGenerator;

public class MaskMutation implements MutationOperator<IntegerSolution> {

	private double mutationProbability;
	private MersenneTwisterGenerator random;

	public MaskMutation(double mutationProbability) {
		if (mutationProbability < 0) {
			throw new JMetalException("Mutation probability is negative: "
					+ mutationProbability);
		}
		this.mutationProbability = mutationProbability;

		random = new MersenneTwisterGenerator();
	}

	@Override
	public IntegerSolution execute(IntegerSolution source) {
		for (int gene = 0; gene < source.getNumberOfVariables(); gene++) {
			if (random.nextDouble() < mutationProbability) { // O gene vai ser modificado
				int value = source.getVariableValue(gene);
				int max = source.getUpperBound(gene);
				int min = source.getLowerBound(gene);
								
				if (random.nextDouble() < 0.5) {
					if(++value > max){
						value = max;
					}
					
					source.setVariableValue(gene, value);
				} else {
					if(--value < min){
						value = min;
					}
					
					source.setVariableValue(gene, value);
				}
			}
		}
		
		return source;
	}
}

package jmetal.operators.crossover;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;
import org.uma.jmetal.util.pseudorandom.impl.MersenneTwisterGenerator;

public class HeritageCrossover implements CrossoverOperator<IntegerSolution> {
	
	private MersenneTwisterGenerator random;
	
	public HeritageCrossover() {
		random = new MersenneTwisterGenerator();
	}

	@Override
	public List<IntegerSolution> execute(List<IntegerSolution> source) {
		DefaultIntegerSolution dad = (DefaultIntegerSolution) source.get(0);
		DefaultIntegerSolution mom = (DefaultIntegerSolution) source.get(1);
		
		DefaultIntegerSolution child = new DefaultIntegerSolution(dad);
		
		for (int gene = 0; gene < child.getNumberOfVariables(); gene ++) {
			if(random.nextDouble() > 0.5){
				child.setVariableValue(gene, mom.getVariableValue(gene));	
			}
		}
		
		List<IntegerSolution> offspring = new ArrayList<IntegerSolution>();
		offspring.add(child);

		return offspring;
	}

}
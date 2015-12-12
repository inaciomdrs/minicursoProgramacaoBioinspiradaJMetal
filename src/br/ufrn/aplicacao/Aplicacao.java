package br.ufrn.aplicacao;

import java.util.List;

import jmetal.algorithm.ga.SimpleGeneticAlgorithm;
import jmetal.operators.crossover.HeritageCrossover;
import jmetal.operators.mutation.MaskMutation;
import jmetal.operators.selection.SingleObjectiveMinimumRouletteWheelSelection;
import jmetal.problem.DisciplinesNumberProblem;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

public class Aplicacao {

	public static void main(String[] args) {
		Problem<IntegerSolution> problem;
		Algorithm<List<IntegerSolution>> algorithm;
		CrossoverOperator<IntegerSolution> crossover;
		MutationOperator<IntegerSolution> mutation;
		SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;

		int CH = 90;
		problem = new DisciplinesNumberProblem(CH);

		crossover = new HeritageCrossover();

		double mutationProbability = 0.5;
		mutation = new MaskMutation(mutationProbability);

		selection = new SingleObjectiveMinimumRouletteWheelSelection<IntegerSolution>();

		int maxGenerations = 25000;
		int populationSize = 100;
		algorithm = new SimpleGeneticAlgorithm<IntegerSolution>(problem,
				maxGenerations, populationSize, crossover, mutation, selection);

						
		
		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(
				algorithm).execute();
		
		List<IntegerSolution> population = algorithm.getResult() ;
	    long computingTime = algorithmRunner.getComputingTime() ;
	    
	    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
	    
	    printSolutions(population);

	}
	
	public static void printSolutions(List<IntegerSolution> population){
		for(IntegerSolution S : population){
			int nVars = S.getNumberOfVariables();
			
			for (int i = 0; i < nVars; i++) {
				System.out.print(S.getVariableValueString(i) + " ");
			}
			
			System.out.print(" (fitness: "+ S.getObjective(0) + ")\n");
			
		}
	}

}

package jmetal.algorithm.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

public class SimpleGeneticAlgorithm<S extends Solution<?>> extends
		AbstractGeneticAlgorithm<S, List<S>> {

	private static final long serialVersionUID = 9028639674072878532L;

	private Comparator<S> comparator;
	private int maxEvaluations;
	private int populationSize;
	private int evaluations;

	private Problem<S> problem;

	public SimpleGeneticAlgorithm(Problem<S> problem, int maxEvaluations,
			int populationSize, CrossoverOperator<S> crossoverOperator,
			MutationOperator<S> mutationOperator,
			SelectionOperator<List<S>, S> selectionOperator) {

		this.problem = problem;
		this.maxEvaluations = maxEvaluations;
		this.populationSize = populationSize;

		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.selectionOperator = selectionOperator;

		comparator = new ObjectiveComparator<S>(0);
	}

	@Override
	protected void initProgress() {
		this.evaluations = 1;
	}

	@Override
	protected void updateProgress() {
		this.evaluations++;
	}

	@Override
	protected boolean isStoppingConditionReached() {
		return this.evaluations == this.maxEvaluations;
	}

	@Override
	protected List<S> createInitialPopulation() {
		List<S> population = new ArrayList<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			S newIndividual = problem.createSolution();
			population.add(newIndividual);
		}
		return population;
	}

	@Override
	protected List<S> evaluatePopulation(List<S> population) {
				
		for (int solution = 0; solution < population.size(); solution++) {
			this.problem.evaluate(population.get(solution));
		}
						
		return population;
	}

	@Override
	protected List<S> selection(List<S> population) {
		int popSize = population.size();
		popSize = (popSize % 2) == 1 ? popSize - 1 : popSize;

		List<S> matingPopulation = new ArrayList<S>(popSize);

		for (int i = 0; i < popSize; i++) {
			S solution = selectionOperator.execute(population);
			matingPopulation.add(solution);
		}

		return matingPopulation;
	}

	@Override
	protected List<S> reproduction(List<S> population) {
		int popSize = population.size() / 2;

		List<S> offspringPopulation = new ArrayList<>(popSize);

		for (int i = 0; i < popSize; i += 2) {
			List<S> parents = new ArrayList<>(2);
			parents.add(population.get(i));
			parents.add(population.get(i + 1));

			List<S> offspring = crossoverOperator.execute(parents);
			for (S s : offspring) {
				mutationOperator.execute(s);
				offspringPopulation.add(s);
			}
		}

		return offspringPopulation;
	}

	@Override
	protected List<S> replacement(List<S> population,
			List<S> offspringPopulation) {

		Collections.sort(population, comparator);
		Collections.sort(offspringPopulation, comparator);

		for (int i = population.size() - 1, j = 0; (i > population.size()
				- offspringPopulation.size())
				&& (j < offspringPopulation.size()); i--, j++) {

			population.remove(i);
			population.add(offspringPopulation.get(j));

		}

		return population;
	}

	@Override
	public List<S> getResult() {
		Collections.sort(getPopulation(), comparator) ;
	    return getPopulation();
	}

}

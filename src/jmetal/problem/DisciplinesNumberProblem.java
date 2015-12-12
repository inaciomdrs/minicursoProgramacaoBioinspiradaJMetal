package jmetal.problem;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;

/**
 * Implementa o Problema de Quantidade de disciplinas.
 * Neste problema, o desafio é distribuir uma dada carga horária
 * em disciplinas de 30, 60 e 90 horas, de modo que hajam 
 * disciplinas com tais cargas horárias horárias de forma
 * a soma total das cargas horárias destas disciplinas totalize
 * (ou se aproxime o máximo possível) a carga horária fornecida.
 * 
 * Matematicamente falando, o problema em questão consiste em
 * minimizar a equação abaixo:
 * 
 * 30x + 60y + 90z = CH
 * 
 * Onde CH (a carga horária) é informada pelo usuário. 
 * 
 * @author inacio-medeiros
 *
 */
public class DisciplinesNumberProblem extends AbstractIntegerProblem {
	
	private static final long serialVersionUID = 3672644720509428425L;
	private int cargaHoraria;

	public DisciplinesNumberProblem(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
		
		setNumberOfVariables(3);
		setNumberOfObjectives(1);
		setName("DisciplinesNumberProblem");
		
		List<Integer> lowerLimit = new ArrayList<Integer>(getNumberOfVariables());
		List<Integer> upperLimit = new ArrayList<Integer>(getNumberOfVariables());
		
		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(0);
			upperLimit.add(9);
		}
		
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
					
	}

	@Override
	public void evaluate(IntegerSolution solution) {
		int[] variables = new int[getNumberOfVariables()];  
		
		for (int i = 0; i < variables.length; i++) {
			variables[i] = solution.getVariableValue(i);
		}
		
		int sum = 30*variables[0] + 60*variables[1] + 90*variables[2];
		
		solution.setObjective(0, Math.abs(this.cargaHoraria-sum));
	}

}

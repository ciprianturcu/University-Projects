#include <stdlib.h>
#include <stdio.h>

int gen_pascal_element(int n,int k,int last)
{
	/// <summary>
	/// function that generates the element of a specific row using Combinations of n taken as k. n!/((n-k)!k!)
	/// </summary>
	/// <param name="n">row number</param>
	/// <param name="k">element number on that specific row</param>
	/// <param name="last"></param>
	/// <returns>the integer number representig the value of the combination</returns>
	return last * (n - k + 1) / (k);
}

int gen_pascal_triangle(int n)
{
	/// <summary>
	/// function to generate every number of the pascal triangle of depth n and print it to the screen
	/// </summary>
	/// <param name="n">depth of the triangle</param>
	/// <returns>prints to the screen the pascal triangle of depth n</returns>
	int last;
	int result[100] = { 0 };
	printf("1\n");
	for (int i = 1; i <= n; i++)
	{
		for (int j = 0; j < 100; j++)
		{
			result[j] = 0;
		}
		result[0] = 1;
		printf("%d ", result[0]);
		for (int k = 1; k <= i; k++)
		{
			last = result[k - 1];
			result[k] = gen_pascal_element(i, k, last);
			printf("%d ", result[k]);
		}
		printf("\n");
	}
	return 0;
}

int verifiy_prime_number(int n)
{
	/// <summary>
	/// function to verify if a given number is prime
	/// </summary>
	/// <param name="n">number to be verified</param>
	/// <returns> 1 if prime, 0 otherwise </returns>
	if (n == 1)
	{
		return 0;
	}
	int d = 2;
	while (d * d <= n)
	{
		if (n%d == 0)
			return 0;
		d++;
	}
	return 1;
}


int long_cont_subseq_prime_nr(int n, int v[],int *maxx, int *max_poz)
{
	/// <summary>
	/// function to determine the longest subsequence of contiguous prime numbers from the vector.
	/// </summary>
	/// <param name="n">length of the vector</param>
	/// <param name="v">vector containing the elements</param>
	/// <param name="maxx">max length of a subsequence of prime numbers</param>
	/// <param name="max_poz">start position of the longest subsequence</param>
	/// <returns>modifies the value of the maximum length and the position of the longest subsequence of contiguous prime numbers from the vector.</returns>
	int result_maxx = 0;
	int current_max = 0;
	int result_max_poz = 0;
	int prime_condition = 0;
	int current_poz = 0;
	
	for (int i = 0; i < n; i++)
	{
		prime_condition = verifiy_prime_number(v[i]);
		if ((prime_condition == 1) && (current_max == 0))
		{
			current_max = 1;
			current_poz = i;
		}
		else if((prime_condition == 1) && (current_max !=0))
		{
			current_max++;
		}
		else
		{
			if (current_max > result_maxx)
			{
				result_maxx = current_max;
				result_max_poz = current_poz;
				current_max = 0;
				current_poz = i;
			}
		}
	}
	if (current_max > result_maxx)
	{
		result_maxx = current_max;
		result_max_poz = current_poz;
	}
	*maxx = result_maxx;
	*max_poz = result_max_poz;
	return 0;
}

int print_longest_sequence(int n,int poz, int v[],int maxx)
{
	/// <summary>
	/// Fucntion that takes the longest subsequence of contiguous prime numbers from a vector and prints it.
	/// </summary>
	/// <param name="n">length of the whole vector</param>
	/// <param name="poz"> start position of the vector</param>
	/// <param name="v"> vector containing the longest subsequence</param>
	/// <param name="maxx"> nr of elements in the subsequence</param>
	/// <returns></returns>
	if (maxx != 0)
	{
		printf("The longest subsequence of prime nr from the vector is:\n");
		for (int i = poz; i < poz + maxx; i++)
		{
			printf("%d ", v[i]);
		}
		printf("\n");
	}
	else
	{
		printf("There is no prime number in the vector.\n");
	}
	return 0;
}

int print_main_menu()
{
	/// <summary>
	/// Function to print the main menu
	/// </summary>
	printf("\n");
	printf("1.Read vector.\n");
	printf("2.Read a sequence of numbers.\n");
	printf("3.Print the pascal triangle of level n.\n");
	printf("4.Find the longest contiguous subsequence of prime numbers from a vector.\n");
	printf("5.Determine the 0 digits of the product of the read sequence of numbers\n");
	printf("6.Exit.\n");
	printf("\n");
	return 0;
}

int print_nr_of_zeros(int nr_of_zeros)
{
	/// <summary>
	/// Function to print the number of zeros
	/// </summary>
	/// <param name="nr_of_zeros">nr of zeros</param>
	printf("\n");
	printf("The nr of zeros of the product of the given sequence is: ");
	printf("%d",nr_of_zeros);
	printf("\n");
	return 0;
}

int read_input_option()
{
	/// <summary>
	/// Function to read the option menu
	/// </summary>
	/// <returns>number read from keyboard representing the chosen option</returns>
	int n;
	printf("Option:\n");
	scanf_s("%d", &n);
	return n;
}

int read_input_option_1()
{
	/// <summary>
	/// Function to read the depth of the pascal triangle.
	/// </summary>
	/// <returns>number read from keyboard representing the depth</returns>
	int n;
	printf("Input level of pascal triangle:\n");
	scanf_s("%d", &n);
	return n;
}

int read_input_option_2()
{
	/// <summary>
	/// Function to read the length of the vector.
	/// </summary>
	/// <returns>number read from keyboard representing the length</returns>
	int n;
	printf("Input length of the vector:\n");
	scanf_s("%d", &n);
	return n;
}

int read_vector(int v[], int vector_length)
{
	/// <summary>
	/// Function to read a vector from keyboard
	/// </summary>
	/// <param name="v">memory location where elements are stored</param>
	/// <param name="vector_length">nr of elements to be read</param>
	/// <returns>a vector of the read elements </returns>
	printf("vector: ");
	for (int i = 0; i < vector_length; i++)
	{
		scanf_s("%d", &v[i]);
	}
	return 0;
}

int read_sequence(int seq[])
{
	/// <summary>
	/// Function to read a sequence of numbers until 0 is given.
	/// </summary>
	/// <param name="seq">sequence of numbers</param>
	int x;
	int i = 1;
	printf("Enter sequence of numbers(To exit put 0):\n");
	scanf_s("%d", &x);
	while (x != 0)
	{
		seq[i] = x;
		i++;
		scanf_s("%d", &x);
	}
	return i-1;
}

int determine_nr_of_zeros_of_read_sequence(int seq[], int sequence_length)
{
	/// <summary>
	/// Function to determine the nr of zeros of the given sequence.(only numbers that can create zeros).
	/// </summary>
	/// <param name="seq">sequence of numbers</param>
	/// <returns>nr of zeros of the product of the numbers from the sequence</returns>
	int nr_of_2 = 0;
	int nr_of_5 = 0;
	int i = 1;
	while (i<=sequence_length)
	{
		if (seq[i] % 2 == 0)
		{
			nr_of_2 = nr_of_2 + 1;
		}
		if (seq[i] % 5 == 0)
		{
			nr_of_5= nr_of_5 + 1;
		}
		i++;
	}
	if (nr_of_2 > nr_of_5)
	{
		return nr_of_5;
	}
	else
	{
		return nr_of_2;
	}
}

int main()
{
	/// <summary>
	/// makes the neccessary call functions for printing the menu reading input and solving a problem. 
	/// </summary>
	/// <returns> result of any given option or exists the program. </returns>
	int maxx=0, max_poz=0;
	int run = 1;
	int option;
	int triangle_lvl;
	int vector_length=0;
	int sequence_length=0;
	int nr_of_zeros;
	int* v = malloc(sizeof(int)*vector_length);
	int* seq = malloc(sizeof(int) * vector_length);
	while(run)
	{
		print_main_menu();
		option = read_input_option();
		switch (option)
		{
		case 1:
		{
			vector_length = read_input_option_2();
			read_vector(v, vector_length);
			break;
		}
		case 2:
		{
			sequence_length = read_sequence(seq);
			break;
		}
		case 3:
		{
			triangle_lvl = read_input_option_1();
			gen_pascal_triangle(triangle_lvl);
			break;
		}
		case 4:
		{
			if (vector_length != 0)
			{
				long_cont_subseq_prime_nr(vector_length, v, &maxx, &max_poz);
				print_longest_sequence(vector_length, max_poz, v, maxx);
			}
			else
			{
				printf("No vector was read. Please read vector.\n");
			}
			break;
		}
		case 5:
		{
			nr_of_zeros= determine_nr_of_zeros_of_read_sequence(seq,sequence_length);
			print_nr_of_zeros(nr_of_zeros);
			break;
		}
		case 6:
		{
			printf("Program exit.\n");
			run = 0;
			return 0;
			break;
		}
		default:
			printf("Error, no such command!\n");
		}
	}
	return 0;
}
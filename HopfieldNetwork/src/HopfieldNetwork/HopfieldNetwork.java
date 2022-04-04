package HopfieldNetwork;

public class HopfieldNetwork {

    public int N; // Size of the Vectors
    public int M; // Number of Vectors
    public double[][] vectors;
    public double[][] T;
    public double[][] testVector;
    public double[][] resultVector;

    public HopfieldNetwork() {
    }

    public HopfieldNetwork(int vectorNumber, int vectorSize, double[][] vectors) {
        N = vectorNumber;
        M = vectorSize;
        this.vectors = vectors;
        //FormMatrixT(vectors);
    }

    public void PrintVectors() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(vectors[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public double[][] ConvertToBipolar(double[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] bmat = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] <= 0) {
                    bmat[i][j] = -1;
                } else {
                    bmat[i][j] = 1;
                }
            }
        }
        return bmat;
    }

    public void FormMatrixT() {
        double[][] step1 = MatrixMult(MatrixTranspose(vectors), vectors);
        double[][] step2 = MatrixSub(step1, MatrixScalarMultiplication(IdentityMatrix(N),M));
        double[][] step3 = MatrixScalarMultiplication(step2, (double)1/N);
        T = step3;
        System.out.println("Matrix T:");
        System.out.println(StringMatrix(T));
        System.out.println("");
    }

    public double[][] CalculateOutputVector() {
        double[][] outputVec = MatrixTranspose(testVector);
        double[][] prevOutputVec = MatrixTranspose(testVector);
        boolean equal = false, prevEqual = false;
        System.out.println("Output Vectors");
        do {
            resultVector = Signum(MatrixMult(T, outputVec));
            equal = MatrixEqual(resultVector, outputVec);
            prevEqual = MatrixEqual(resultVector, prevOutputVec);
            prevOutputVec = outputVec;
            outputVec = resultVector;
            System.out.println(StringMatrix(resultVector));
        } while ((equal == false) && (prevEqual == false));
        outputVec = MatrixTranspose(outputVec);
        prevOutputVec = MatrixTranspose(prevOutputVec);
        if(equal == true)
            return MatrixTranspose(resultVector);
        else{
            double[][] cycleVectors = new double[2][MatrixTranspose(outputVec).length];
            cycleVectors[0] = outputVec[0];
            cycleVectors[1] =  prevOutputVec[0];
            return cycleVectors;
        }
    }

    // To be removed
    public String StringMatrix(double[][] vector) {
        int cols = vector.length;
        int rows = vector[0].length;
        String result = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result += String.format("%,.5f",vector[j][i]) + "  \t  ";
            }
            result += "\n";
        }
        return result;
    }

    public double[][] MatrixTranspose(double[][] matrix) {
        int rows = matrix[0].length;
        int columns = matrix.length;
        double[][] result = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    public double[][] MatrixMult(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int columns1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int columns2 = matrix2[0].length;

        if (columns1 != rows2) {
            System.out.println("Invalid matrix input: The sizes do not fit");
            return null;
        }

        double[][] result = new double[rows1][columns2];
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < columns1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    public boolean MatrixEqual(double[][] matrix1, double[][] matrix2) {
        int row1 = matrix1.length;
        int col1 = matrix1[0].length;
        int row2 = matrix2.length;
        int col2 = matrix2[0].length;

        if ( (row1 != row2) || (col1 != col2)) {
            return false;
        }

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                if(matrix1[i][j] != matrix2[i][j])
                    return false;
            }
        }
        return true;
    }

    public double[][] MatrixScalarMultiplication(double[][] matrix, double scalar) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = scalar * matrix[i][j];
            }
        }
        return result;
    }

    public double[][] MatrixSub(double[][] matrix1, double[][] matrix2) {
        int rows = matrix1[0].length;
        int columns = matrix1.length;
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return result;
    }

    public double[][] IdentityMatrix(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
                if (i == j) {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    public double[][] Signum(double[][] mat) {    
        int rows = mat.length;
        int cols = mat[0].length;
        double[][] res = new double[rows][cols];
        
        for(int i = 0 ; i < rows; i++){
            for(int j =0 ; j < cols; j++){
                if(mat[i][j] < 0){
                    res[i][j] = -1;
                }
                else{
                    res[i][j] = 1;
                }
            }
        }
        return res;
    }
}

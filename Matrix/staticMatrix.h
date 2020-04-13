//Piotr Grabowski
#pragma once
#include <algorithm>
#include <iostream>

class Uninitialized{};

template<typename T, int N, int M>
class Matrix{
    size_t numberOfRows = N;
    size_t numberOfCols = M;

public:
    T data[N*M];
    Matrix()
            : numberOfRows(N), numberOfCols(M){
        for(int i = 0; i < numberofColumns()*numberofColumns(); i++)
            data[i] = 0;
        std::cout << "constructor of " << numberOfRows << "x" << numberOfCols << " matrix" << std::endl;
    }


    explicit Matrix(Uninitialized, size_t rows = N , size_t cols = M){
        std::cout << "Constructor without initialization" << std::endl;
    }

    explicit Matrix(size_t rows = N , size_t cols = M)
        : numberOfRows(rows), numberOfCols(cols){
        std::cout << "Constructor static Matrix " << numberOfRows << " x " << numberOfCols << std::endl;
    }

    Matrix(std::initializer_list<std::initializer_list<double> > list){
        numberOfRows = list.size();
        size_t maxSize = 0;
        for(const auto & row : list)
            maxSize = std::max(maxSize, row.size());
        numberOfCols = maxSize;
        double *p = data;
        for(auto row : list){
            double *copyend = std::copy(row.begin(), row.end(), p);
            std::fill(copyend, p+numberOfCols, 0.0);
            p += numberOfCols;
        }
        std::cout << "constructor of " << numberOfRows << "x" << numberOfCols << " matrix from initializer_list" << std::endl;
    }

    Matrix(const Matrix & m)
            : numberOfRows(m.numberOfRows), numberOfCols(m.numberOfCols){
        std::copy_n(m.data, numberOfCols * numberOfRows, data);
        std::cout << "copy constructor " << std::endl;
    }

    explicit Matrix(const Matrix<T,0,0> &m)
            : numberOfRows(m.numberofRows()), numberOfCols(m.numberofColumns()){
        std::copy_n(m.data, numberOfCols * numberOfRows, data);
        std::cout << "conversion from dynamic to static " << std::endl;
    }

    T operator() (size_t row, size_t col) const{
        return data[(row-1)*numberOfCols + col-1];
    }

    T & operator() (size_t row, size_t col){
        return data[(row-1)*numberOfCols + col-1];
    }

    int numberofRows() const { return numberOfRows; }

    int numberofColumns() const { return numberOfCols; }

    friend Matrix operator+(const Matrix<T,N,M> &a, const Matrix<T,N,M> &b)  {
        Uninitialized x;
        Matrix result(x);
        for(int i = 0; i <= a.numberOfCols * a.numberOfRows; i++)
            result.data[i] = a.data[i] + b.data[i];
        return result;
    }

    template<int P>
    friend Matrix<T,N,P> operator*(const Matrix<T,N,M> &a, const Matrix<T,M,P> &b) {
        Matrix <T,N,P> result(Uninitialized{});
        for(int i = 1; i <= N; i++) {
            for (int j = 1; j <= P; j++) {
                T temp = T{};
                for (int h = 1; h <= M; h++) {
                    temp += a(i,h) * b(h, j);
                }
                result(i,j) = temp;
            }
        }
        return result;
    }
};

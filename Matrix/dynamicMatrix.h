//Piotr Grabowski
#pragma once
#include "staticMatrix.h"
class MatrixException{
    char x;

public:
    std::string what(){
        if(x == 'D') return " Incompatible dimensions in operator +";
        else  return " Incompatible dimensions in operator *";
    }

    explicit MatrixException(char a) : x(a){}
};
template <typename T>
class Matrix<T,0,0>{
    size_t numberOfCols=0;
    size_t numberOfRows=0;
public:
    T* data;
    Matrix(){
        for(int i = 0; i < numberOfCols*numberOfRows; i++)
            data = T();
        std::cout << "constructor default of dynamic Matrix" << std::endl;
    }

    Matrix(size_t rows, size_t cols) {
        numberOfRows = rows;
        numberOfCols = cols;
        data = new T [rows*cols];
        std::fill_n(data,rows*cols,T());
        std::cout << "Constructor of dynamic " << numberOfRows << " x " << numberOfCols << " Matrix" << std::endl;
    }

    Matrix(Uninitialized, size_t rows = 0 , size_t cols = 0) : numberOfRows(rows),numberOfCols(cols){
        data = new T [numberOfRows*numberOfCols];
        std::cout << "Constructor of dynamic " << numberOfRows << " x " << numberOfCols << " Matrix without initialization" << std::endl;
    }

    Matrix(std::initializer_list<std::initializer_list<T> > list){

        numberOfRows = list.size();
        size_t maxSize = 0;
        for(const auto & row : list)
            maxSize = std::max(maxSize, row.size());
        numberOfCols = maxSize;
        data = new T[numberOfCols*numberOfRows];

        T *p = data;
        for(auto row : list){
            T *copyend = std::copy(row.begin(), row.end(), p);
            std::fill(copyend, p+numberOfCols, T());
            p += numberOfCols;
        }

        std::cout << "constructor of  " << numberOfRows << "x" << numberOfCols << " dynamic matrix from initializer_list" << std::endl;
    }

    Matrix(Matrix && m)
            : numberOfRows(m.numberOfRows), numberOfCols(m.numberOfCols){
        data = new T[numberOfCols*numberOfRows];
        std::copy_n(m.data, numberOfCols * numberOfRows, data);
        m.data = nullptr;
        std::cout << "move constructor" << std::endl;
    }


    Matrix(const Matrix & m)
            : numberOfRows(m.numberOfRows), numberOfCols(m.numberOfCols), data(new T [numberOfCols*numberOfRows]){
        std::copy_n(m.data, numberOfCols * numberOfRows, data);
        std::cout << "copy constructor dynamic Matrix " << std::endl;
    }

    template<int N, int M>
    explicit Matrix(const Matrix<T,N,M> & m)
            : numberOfRows(m.numberofRows()), numberOfCols(m.numberofColumns()), data(new T [numberOfCols*numberOfRows]){
        std::copy_n(m.data, numberOfCols * numberOfRows, data);
        std::cout << "copy constructor dynamic Matrix " << std::endl;
    }

    T operator() (size_t row, size_t col) const{
        return data[(row-1)*numberOfCols + col-1];
    }

    T & operator() (size_t row, size_t col){
        return data[(row-1)*numberOfCols + col-1];
    }

    int numberofRows() const { return numberOfRows; }
    int numberofColumns() const { return numberOfCols; }

    friend Matrix<T,0,0> operator+(const Matrix<T,0,0> &a, const Matrix<T,0,0> &b)  {
        if(a.numberofRows() != b.numberOfRows || a.numberofColumns() != b.numberOfCols)
            throw MatrixException('D');
        Matrix<T,0,0> res(a.numberOfRows,a.numberOfCols);
        for(int i = 0; i < a.numberOfCols * a.numberOfRows; i++)
            res.data[i] = a.data[i] + b.data[i];
        return std::move(res);
    }

    friend Matrix operator*(const Matrix &a, const Matrix &b) {
        if(a.numberOfCols != b.numberOfRows )
            throw MatrixException('M');
        Uninitialized x;

        Matrix result(x,a.numberOfRows,b.numberOfCols);

        for(int i = 1; i <= a.numberofRows(); i++) {
            for (int j = 1; j <= b.numberofColumns(); j++) {
                result(i,j) = 0;
                for (int h = 1; h <= a.numberofColumns(); h++) {
                    result(i,j) += a(i,h) * b(h, j);
                }
            }
        }
        return result;
    }
};

template<typename T, int N, int M>
Matrix<T,N,M> operator+(const Matrix<T,N,M> &a, const Matrix<T,0,0> &b){
    if(a.numberofRows() != b.numberofRows() || a.numberofColumns() != b.numberofColumns())
        throw MatrixException('D');
    Matrix<T,N,M> res(a.numberofRows(),a.numberofColumns());
    for(int i = 0; i < a.numberofColumns() * a.numberofRows(); i++)
        res.data[i] = a.data[i] + b.data[i];
    return res;
}

template<typename T, int N, int M>
Matrix<T,N,M> operator+(const Matrix<T,0,0> &a, const Matrix<T,N,M> &b){
    if(a.numberofRows() != b.numberofRows() || a.numberofColumns() != b.numberofColumns())
        throw MatrixException('D');
    Matrix<T,N,M> res(a.numberofRows(),a.numberofColumns());
    for(int i = 0; i < a.numberofColumns() * a.numberofRows(); i++)
        res.data[i] = a.data[i] + b.data[i];
    return res;
}

template<typename T, int N, int M>
Matrix<T,0,0> operator*(const Matrix<T,N,M> &a, const Matrix<T,0,0> &b){
    if(a.numberofColumns() != b.numberofRows() )
        throw MatrixException('M');
    Matrix<T,0,0> result(Uninitialized{}, a.numberofRows(), b.numberofColumns());
    for(int i = 1; i <= a.numberofRows(); i++) {
        for (int j = 1; j <= b.numberofColumns(); j++) {
            result(i,j) = T();
            for (int h = 1; h <= a.numberofColumns(); h++) {
                result(i,j) += a(i,h) * b(h, j);
            }
        }
    }
    return result;
}

template<typename T, int N, int M>
Matrix<T,0,0> operator*(const Matrix<T,0,0> &a, const Matrix<T,N,M> &b){
    if(a.numberofColumns() != b.numberofRows() )
        throw MatrixException('M');
    Matrix<T,0,0> result(Uninitialized{}, a.numberofRows(), b.numberofColumns());
    for(int i = 1; i <= a.numberofRows(); i++) {
        for (int j = 1; j <= b.numberofColumns(); j++) {
            result(i,j) = T();
            for (int h = 1; h <= a.numberofColumns(); h++) {
                result(i,j) += a(i,h) * b(h, j);
            }
        }
    }
    return result;
}

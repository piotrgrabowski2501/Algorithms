#include <iostream>
#include <vector>
#include <list>
#include <cmath>
#include <deque>
using namespace std;

template<typename T>
T sqr(T a){return a*a;}
template<>
string sqr<string>(string a){
    string x = a+a;
    return x;
}

template<int N>
int mod(int x){return x % N;}
template <>
int mod<0>(int x){return -x;}

template<typename Container>
void print(const Container & v){
    for( auto iter=v.begin(); iter != v.end(); iter++ )
        cout << *iter << " ";
    cout << endl;
}

template<typename C, typename F>
C apply (const C& c, F f){
    C tmp;
    for( auto iter=c.begin(); iter != c.end(); iter++ ){
        auto tmp1 = f(*iter);
        tmp.push_back(tmp1);
    }
    return tmp;
}

struct Rational{
    int nominator=0, denominator=1;
    friend bool operator<(const Rational & a, const Rational & b){
        return a.nominator*b.denominator < b.nominator*a.denominator;
    }
};

template<typename T>
int compare(T a, T b){
    if(a < b) return 1;
    else if(b < a) return -1;
    else return 0;
}

template<typename T>
int compare(T* a, T*b){
    if(*a < *b) return 1;
    else if(*b < *a) return -1;
    else return 0;
}

template <>
int compare(const char a[], const char b[]){
    string a1(a);
    string b1(b);
    int size = min(a1.size(),b1.size());
    for(int i = 0; i < size; i++) {
        if (a1[i] < b1[i]) return 1;
        if (b1[i] < a1[i]) return -1;
    }
    return 0;
}

template<typename T,T (*f)(T a), int N>
void process(T array[]){
    for(int i = 0; i < N; i++)
        array[i] = f(array[i]);
}

bool biggerThan5(int x){ return x>5; }

template<template <typename,  typename> class OutContainer, typename T, typename Alloc, template < typename,  typename> class InContainer,
        typename Predicate>
OutContainer<T,Alloc>   selectIf(InContainer<T,Alloc>  c, Predicate p){
    OutContainer<T,Alloc> a;
    for( auto iter=c.begin(); iter != c.end(); iter++ ){
        if(p( *iter ))
            a.push_back( *iter );
    }
    return a;
}

int main(){

    // function template  sqr<T>
    cout << sqr(4) << endl;             // 16
    cout << sqr(14.5) << endl;          // 210.25
    cout << sqr(string("hey")) << endl; // heyhey

    // function template mod<N>
    cout << mod<5>(131) << endl;        // 1
    cout << mod<7>(131) << endl;        // 5

    // function template print
    std::vector<int> v = {1, 21, 34, 4, 15};
    print(v);                    // 1 21 34 4 15

    std::list<double> l = {1, 2.1, 3.2, 6.3};
    print(l);                    // 1 2.1 3.2 6.3

    // function template apply
    auto w = apply(v, sqr<int>);
    print(w);  // 1 441 1156 16 225

    auto w2 = apply(w, mod<5> );
    print(w2); // 1 1 1 1 0

    auto w3 = apply(w, mod<0> );
    print(w3); // -1 -441 -1156 -16 -225

    auto l2 = apply(l, sqr<double>);
    print(l2); // 1 4.41 10.24 39.69

    auto l3 = apply(l2, mod<5>);
    print(l3); // 1 4 0 4

    // function sin is overloaded, we need to cast it
    auto l4 = apply(l3, static_cast<double(*)(double)>(std::sin));
    print(l4); // 0.841471 -0.756802 0 -0.756802


    int a = 1, b=-6;
    float  y= 1.0 + 1e20 - 1e20, x = 1.0;
    Rational p{2}, q{1,4}, r{8,4};
    cout << "values\n";
    cout << compare(a,b) << " " << compare(b,a) << " " << compare(a,a) << endl;
    cout << compare(x,y) << " " << compare(y,x) << " " << compare(x,x) << endl;
    cout << compare(p,q) << " " << compare(q,p) << " " << compare(p,r) << endl;
    cout << "pointers\n";
    cout << compare(&a,&b) << " " << compare(&b,&a) << " " << compare(&a,&a) << endl;
    cout << compare(&x,&y) << " " << compare(&y,&x) << " " << compare(&x,&x) << endl;
    cout << compare(&p,&q) << " " << compare(&q,&p) << " " << compare(&p,&r) << endl;

    const char *s  = "Alpha", *t="Alfa", *t2 = "Alfa";
    cout << "C-strings\n";
    cout << compare(s,t) << " " << compare(t,s) << " " << compare(t,t)
         << " " << compare(t, t2) << " " << compare(t, "Beta") << endl;

	// function template process
    double arr[] = {1, 2, 3, 4};
    process<double, sin, 4> (arr);
    for( auto x: arr)
        cout << x << " "; // 0.841471 0.909297 0.14112 -0.756802
    cout << endl;

    auto print = [](auto v) {
        for(auto x: v) cout << x << " ";
        cout << endl;
    };

    std::vector<int> vec={1, 2, 13, 4, 5, 54};
    std::list<int> result = selectIf<std::list>(vec, biggerThan5);
    print(result);  //  13 54

    auto result2 = selectIf<std::deque>(vec, [](int x)->bool{ return x%2; } );
    print(result2); //  1 13 5

    auto result3 = selectIf<std::vector>(result2, biggerThan5);
    print(result3); //  13

    return 0;
}

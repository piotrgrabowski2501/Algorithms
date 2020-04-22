//Piotr Grabowski
#include <iostream>
#include <typeinfo>
using namespace std;

size_t Global_counter = 0;
size_t Global_size = 0;

template <class T>
struct CountedClass{
protected:
    static size_t this_class_counter;
    static size_t this_class_size;
    static size_t size;
public:
    static size_t numberOfObjects(){
        return this_class_counter;
    }
    static size_t totalNumberOfObjects(){
        return Global_counter;
    }
    static size_t totalSize(){
        return Global_size;
    }
    explicit CountedClass(int a){
        ++this_class_counter;
        ++Global_counter;
        this_class_size += a;
        Global_size += a;
        size = a;
    }
    CountedClass() = default;
    ~CountedClass(){
        --this_class_counter;
        --Global_counter;
        this_class_size -= size;
        Global_size -= size;
    }
};
template <class T>
size_t CountedClass<T>::this_class_counter = 0;
template <class T>
size_t CountedClass<T>::this_class_size = 0;
template <class T>
size_t CountedClass<T>::size = 0;


template <typename T, int N>
struct A : public CountedClass<A< T,N> >{
    T data[N];
    A() : CountedClass<A>(sizeof(A)){}
};

template <typename T, typename S>
struct P : CountedClass<P<T,S>>{
    T a = T{};
    S b = S{};
    P() : CountedClass<P>(sizeof(P)){}
    P(T && a, S && b) : a(std::forward<T&&>(a)),
                        b(std::forward<S&&>(b)), CountedClass<P>(sizeof(P)) {}
    P(P& a) : a(a.a), b(a.b),CountedClass<P>(sizeof(P)){}
};


double f(int x, double y, const int & z, int & w){
    w += 2;
    cout << x << " " << y<< " " << z << " " << w <<endl;
    return (x*y - z*w);
}
int counter = 0;
void showNames()
{
    counter = 0;
}
template <typename T, typename... Types>
void showNames(T var1, Types... var2)
{
    cout << ++counter << " > " << "[" << typeid(var1).name() << "] = " << var1;
    cout << endl;
    showNames(var2...) ;
}
template <typename T>
class Proxy{
public:
    T function;
    Proxy(T f) : function(f){}
    template <typename... Types>
    auto operator()(Types&&... var2){
        showNames(var2...);
        return function(var2...);
    }
};
template <typename T>
Proxy<T>  make_proxy(T fun) {
    return std::forward<T &&>(fun);
}


int main(){
    using At = A<int,10>;
    using Pt = P<int, double>;
    using APt = A<Pt, 5>;
    At a1, a2;
    At * pa = new At{};

    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;

    Pt p1{1, 5.3};
    Pt p3{p1};
    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;
    cout << Pt::numberOfObjects() << " " << Pt::totalNumberOfObjects()
         << " " << Pt::totalSize() << endl;

    delete pa;
    cout << At::numberOfObjects() << " " << At::totalNumberOfObjects()
         << " " << At::totalSize() << endl;

    // Here total size counts elements of A::data twice.
    APt ap;
    cout << Pt::numberOfObjects() << " " << Pt::totalNumberOfObjects()
         << " " << Pt::totalSize() << endl;
    cout << APt::numberOfObjects() << " " << APt::totalNumberOfObjects()
         << " " << APt::totalSize() << endl;

    
    int x = 4;
    const int y = 8;
    showNames(x, 4.5, y, f);
    showNames(1, 1.0f, 1.0, 1LL, &x, &y);

    cout << "Proxy" << endl;
    auto p = make_proxy(f);
    auto result1 = p(12, 5.1, y, x);
    cout << "result1 = " << result1 << endl;
    auto result2 = p(12, 5.1, y, x);
    cout << "result2 = " << result2 << endl;
    auto result3 = p(3, 3, 5, x);
    cout << "result3 = " << result3 << endl;

    cout << "Proxy" << endl;
    auto g = make_proxy([](int &x, int & y){ y = x; return y; }) ;
    cout << g(5, x) << endl;
    cout << "x = " <<  x << endl;
    return 0;
    return 0;
}

#include <bits/stdc++.h>
using namespace std;
const int N = 2000;

mt19937_64 rng(chrono::steady_clock::now().time_since_epoch().count());
long long myRand(long long limit) {
    return rng() % limit;
}

int main() {
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
            cout << (myRand(10) + 1) << " \n"[j + 1 == N];
        }
    }
    return 0;
}


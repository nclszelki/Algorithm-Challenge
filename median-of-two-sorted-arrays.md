![#f03c15](https://placehold.it/15/f03c15/000000?text=+) `HARD`
# Median of Two Sorted Arrays

There are two sorted arrays A and B of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume A and B cannot be both empty.

-----------------------------------------------------------------------------------------------------------------
# Solution

Key point: The median is `dividing a set into two equal length subset, that one subset is always greater than the other`

- First cut A into tow parts at a position i

| **left_A** | **right_A** |
|---|---|
|A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1] |

- Do the same for B at a position j

| **left_B** | **right_B** |
|---|---|
|B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1] |

- Put **left_A** and **left_B** in **LEFT_PART**, put **right_A** and **right_B** in **RIGHT_PART**.

| **LEFT_PART** | **RIGHT_PART** |
|---|---|
|A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1] |
|B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1] |

We need to achieve
``` python
1) len(LEFT_PART) == len(RIGHT_PART)
2) max(LEFT_PART) <= min(RIGHT_PART)
```
Then we have divided all elements in {A,B} into two parts with equal length, RIGHT_PART is always greater than LEFT_PART. Then
``` python
median = (max(LEFT_PART) + min(RIGHT_PART))/2
```
How do we ensure 1) and 2)?
we need to make sure that
```python
# Since
# when n + m is even , i + j = m - i + n - j 
# when n + m is odd, place the extra one element in LEFT_PART, i + j = m - i + n - j + 1
# when n >= m (  
i = [0~m] j = (m + n + 1)/2 - i
# (since m+n+1/2 = m+n/2) 
B[j-1] <= A[i] and A[i-1] <= B[j]
```

Now just search in range [0,m] for i that satisifies the above two conditions.

What's a fast search algorithm in a sorted array? 
`BINARY SEARCH`

```Python
<1> Set imin = 0, imax = m, then start searching in [imin, imax]

<2> Set i = (imin + imax)/2, j = (m + n + 1)/2 - i

<3> Now we have len(left_part)==len(right_part). And there are only 3 situations
     that we may encounter:
    <a> B[j-1] <= A[i] and A[i-1] <= B[j]
    
        Means we have found the object `i`, so stop searching.
        
    <b> B[j-1] > A[i]
        
        #`increase` i, when i is increased, j will be decreased.
        #So B[j-1] is decreased and A[i] is increased, and `B[j-1] <= A[i]` may
        #be satisfied.
            
        imin = i+1
        
    <c> A[i-1] > B[j]
    
        # Means A[i-1] is too big. And we must `decrease` i to get `A[i-1]<=B[j]`
        
        imax = i-1
```

When i is found, the median is 
```c
max( A[i-1], B[j-1] ) if m + n is odd
(max(A[i-1], B[j-1]) + min(A[i], B[j])) \ 2  if m + n is even
```

# Edge Cases
- What about when i=0 or i = m, j = 1 or j = n?
In those cases, **A[i-1],B[j-1],A[i],B[j]** may not exists, then there is no need to check B[j-1] <= A[i] and A[i-1] <= B[j].
We would do
```c
if ( i = 0 or j = n or A[i-1] < B[j] ) and
    ( j = 0 or i = m or B[j-1] < A[i] ) where j = ( m + n + 1 ) / 2 - i
```
Normally we'd want the indexes to be in bound: 
```c
if ( i - 1 >= 0 && i - 1 < m && j >= 0 && j < n && A[i - 1] > B[j]) imax = i - 1;
 else if ( j - 1 >= 0 && j - 1 < n && i >= 0 && i < m && B[j - 1] > A[i]) imin = i + 1;
```
- But since `imin` starts with 0 and `imax` starts with m, so it is guaranteed that: **`0 <= i <= m` (1)**
And `j = (m + n + 1)/2 - i`, since `0 <= i <= m`, `(m + n + 1)/2 - m <= j <= (m + n + 1)/2` which can be simplified to `(n - m + 1)/2 <= j <= (m + n + 1)/2`
For the left side: `(n - m + 1)/2 <= j (n - m >= 0)` ==> `(n - m + 1)/2 >= 1/2 = 0` ==> so j >= 0
For the right side: `j <= (m + n + 1)/2` ==> `m <= n` ==> so we have `j <= (2n + 1)/2` ==> and `2n + 1` must be odd, so `(2n + 1)/2 = 2n/2 = n` ==> thus j <= n
So **`0 <= j <= n`(2)** is an implicit condition

With **(1)** and **(2)**, we have
```c
if(i - 1 >= 0 && j < n && A[i - 1] > B[j]) right = i - 1;
 else if(j - 1 >= 0 && i < m && A[j - 1] > B[i]) left = i + 1;
```

- Plus for `j = (m + n + 1)/2 - i`, if `i >= 1`, since `j <= n`, `j < n` is by default.
  Same for `i < m`. So we have:
  
```c
if(i - 1 >= 0 && A[i - 1] > B[j]) right = i - 1;
 else if(j - 1 >= 0 && A[j - 1] > B[i]) left = i + 1;
```

# Solution Implementation
```c++
class Solution {
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        int m = nums1.size(), n = nums2.size(), l = 0, r = m;
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }
        while (l <= r) {
            int i = (l + r) / 2, j = (m + n + 1) / 2 - i;
            if (i && nums1[i - 1] > nums2[j]) {
                r = i - 1;
            } else if (i < m && nums2[j - 1] > nums1[i]) {
                l = i + 1;
            } else {
                int lmax = !i ? nums2[j - 1] : (!j ? nums1[i - 1] : max(nums1[i - 1], nums2[j - 1]));
                if ((m + n) % 2) {
                    return lmax;
                }
                int rmin = i == m ? nums2[j] : (j == n ? nums1[i] : min(nums1[i], nums2[j]));
                return 0.5 * (lmax + rmin);
            }
        }
        return 0.0;
    }
};

```

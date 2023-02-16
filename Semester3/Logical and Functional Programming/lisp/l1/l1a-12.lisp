;a) Write a function to return the dot product of two vectors. https://en.wikipedia.org/wiki/Dot_product

;dot_product(v1,v2)
; nil - v1 or v2 null
; v11*v21+dot_product(v12..v1n,v22..v2m)

(defun dot_product(v1 v2)
    (cond
        ((or (null v1) (null v2)) 0)
        (T (+ (* (car v1) (car v2)) (dot_product (cdr v1) (cdr v2))))
    )
)

(print(dot_product '(4 5 6) '(1 2 3)))


;b) Write a function to return the maximum value of all the numerical atoms of a list, at any level.

(defun maxNb (a b)
    (if (> a b) a b)
)


(defun maxNum (l)
    (cond
        ((null l) -1)
        ((and (null (cdr l)) (numberp (car l))) (car l))
        ((numberp (car l)) (maxNb (car l) (maxNum (cdr l))))
        ((listp (car l)) (maxNb (maxNum (car l)) (maxNum (cdr l))))
        (t (maxNum (cdr l)))
     )
)


;c) All permutations to be replaced by: Write a function to compute the result of an arithmetic expression
;memorised
; in preorder on a stack. Examples:
; (+ 1 3) ==> 4 (1 + 3)
; (+ * 2 4 3) ==> 11 [((2 * 4) + 3)
; (+ * 2 4 - 5 * 2 2) ==> 9 ((2 * 4) + (5 - (2 * 2))

;_expression(op a b)
;a+b - op = "+"
;a-b - op = "-"
;a*b - op = "*"
;a/b - op = "/"

(defun _expression(op a b)
    (cond
        ((string= op "+") (+ a b))
        ((string= op "-") (- a b))
        ((string= op "*") (* a b))
        ((string= op "/") (floor a b))
    )
)
 
;myExoression(l)
;nil - l null
;_expression(l1 l2 l3) + myExpression(l4...ln) - l1 atom and l2 nr and l3 nr
;l1 + myExpression(l2..ln)

(defun myExpression(l)
    (cond
        ((null l) nil)
        ((and (and (numberp (cadr l)) (numberp (caddr l))) (atom (car l))) (cons (_expression (car l) (cadr l) (caddr l)) (myExpression (cdddr l))))
        (T (cons (car l) (myExpression (cdr l))))
    )
)

(defun solve(l)
    (cond
        ((null (cdr l)) (car l))
        (T (solve (myExpression l)))
    )
)

(print (solve '(+ * 2 4 - 5 * 2 2)))

;d) Write a function to return T if a list has an even number of elements on the first level, and NIL on the
;contrary case, without counting the elements of the list.

(defun evenLengthL (l)
    (cond
        ((null l) T)
        ((null (cdr l)) nil)
        (T (evenLengthL (cddr l)))
    )
)

(print (evenLengthL '(1 2 2 4)))
; => T

(print (evenLengthL '(1 2 4 2 4)))
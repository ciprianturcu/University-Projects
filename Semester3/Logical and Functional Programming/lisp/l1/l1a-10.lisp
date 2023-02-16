;a) Write a function to return the product of all the numerical atoms from a list, at superficial level.

;productSup(l)
;1 - l null
;l1 * productSup(l2..ln) - l1 number
;1 * productSup(l2..ln)

(defun productSup(l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (* (car l) (productSup (cdr l))))
        (T (* 1 (productSup (cdr l))))
    )
)

(print (productSup '(1 A 2 3 (3 4 5) T)))

;b) Write a function to replace the first occurence of an element E in a given list with an other element O.

;replaceFirstAp(l e o)
;nil - l
;o + replaceFirstAp(l e o) - l1 = e  
;l1 +replacefirstAp(l e o) 

(defun replaceFirstElement(l e o)
    (cond 
        ((null l) nil)
        ((equal e (car l)) (cons o (cdr l)))
        (T (cons (car l) (replaceFirstElement (cdr l) e o)))
    )
)

(print (replaceFirstElement '(1 2 3 4 5 6 7 6 4 6) 6 0))


;c) Write a function to compute the result of an arithmetic expression memorised in preorder on a stack. Examples:
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

;d) Write a function to produce the list of pairs (atom n), where atom appears for n times in the parameter list. Example:
; (A B A B A C A) --> ((A 4) (B 2) (C 1))

;nrApp(l e)
;0 - null l
;1+nrApp(l2..ln e) - l = e
;nrApp(l2..ln e)
(defun nrApp (l e)
    (cond
        ((null l) 0)
        ((equal (car l) e) (+ 1 (nrApp (cdr l) e)))
        (T (nrApp (cdr l) e))
    )
)

;remov(l e)
;nil - l null
;remove(l2..ln e) - l1 = e
;l1 + remove(l2..ln ,e ) -otherwise


(defun remov (l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) (remov (cdr l) e))
        (T (cons (car l) (remov (cdr l) e)))
    )
)

(defun solve (l)
    (cond
        ((null l) nil)
        (T (cons (list (car l) (nrApp l (car l))) (solve (remov (cdr l) (car l)))))
    )
)

(print (solve '(A B A B A C A)))

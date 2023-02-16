;a) Write a function that inserts in a linear list a given 
;atom A after the 2nd, 4th, 6th, ... element.

;insrt(l e pos)
;nil - l null
;l1 + e + insrt (l2...ln,e)- if pos respects condition
;l1 +insrt (l2...ln,e) - otherwise

(defun insrt(l e pos)
    (cond
        ((null l) nil)
        ((equal (mod pos 2) 0) (cons (car l) (cons e (insrt (cdr l) e (+ 1 pos)))))
        (T (cons (car l) (insrt (cdr l) e (+ 1 pos))))
    )
)

(print (insrt '(1 2 3 4 5 6 7 8 9 10) 69 1))

;b) Write a function to get from a given list the list of all atoms, on any
;level, but reverse order. Example:
;(((A B) C) (D E)) ==> (E D C B A)

;get_atoms(l)
;nil - l null
;get_atoms(l1) - l1 list
;get_atoms(l2..ln) U l1 -otherwise

(defun my_append(l e)
    (cond
        ((null l) e)
        (T (cons (car l) (my_append (cdr l) e)))
    )
)

(defun get_atoms(l)
    (cond
        ((null l) nil)  
        ((listp (car l)) (my_append (get_atoms (cdr l)) (get_atoms (car l))))
        (T (my_append (get_atoms (cdr l)) (list (car l))))
    )
)

(print (get_atoms '(((A B) C) (D E))))

;c) Write a function that returns the 
;greatest common divisor of all numbers in a nonlinear list.


(defun _gcd(a b)
    (cond
        ((and (not (numberp a)) (not (numberp b))) nil)
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((equal b 0) a)
        (T (_gcd b (mod a b)))
    )
)

;listGcd(l)
;nil - l null
;l1 - l1 atom and l2...ln null
;gcd(listGcd(l1),listGcd(l2..ln)) - l1 list
;gcd(l1, listGcd(l2..ln)) 
(defun listGcd(l)
    (cond
        ((null l) nil)
        ((and (atom (car l)) (null (cdr l))) (car l))
        ((listp (car l)) (_gcd (listGcd (car l)) (listGcd (cdr l))))
        (T (_gcd (car l) (listGcd (cdr l))))
    )
)

(print (listGcd '(24 ( 16 (12 A B)) 72)))


;d) Write a function that 
;determines the number of occurrences of a given atom in a nonlinear list.

;nrOfApp(l e)
;0 - l null
;1 + nrOfApp (l2..ln, e) - l1 atom and l1 = e
; nrOfApp(l1,e) + nrOfApp(l2..ln,e) - l1 list
; 0 + nrOfApp(l2..ln) -otherwise


(defun nrOfApp(l e c)
    (cond
        ((null l) c)
        ((listp (car l)) (+ (nrOfApp (car l) e 0) (nrOfApp (cdr l) e c)))
        ((equal e (car l)) (nrOfApp (cdr l) e (+ 1 c)))
        (T (nrOfApp (cdr l) e c))
    )
)

(print (nrOfApp '(1 (3 (5 4 3) (5 3)) 3 3) 3 0))
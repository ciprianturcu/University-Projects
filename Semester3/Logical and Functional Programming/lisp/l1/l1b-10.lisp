; 10. Return the level of a node X in a tree of type (2). The level of the root element is 0.
;CADR L -> LEFT SUBTREE
;CADDR L ->RIGHT SUBTREE
;(A (B) (C (D) (E))) -> type 2

(defun levelNode (L X)
  (cond 
   ((NULL L) NIL)
   ((EQUAL (CAR L) X) 0)
   (T 
    (cond 
     ((NUMBERP (setq check (levelNode (CADR L) X))) (+ 1 check))
     ((NUMBERP (setq check (levelNode (CADDR L) X))) (+ 1 check))
     (T NIL)
    )
   )
  )
)

(print (levelNode '(A (B) (C (D) (E))) 'E))
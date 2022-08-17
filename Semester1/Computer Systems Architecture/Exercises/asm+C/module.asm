bits 32

global _function

segment data public data use32
    


segment code public code use32
;function(int i, *a, *b)
;b-rez
;a-source
;i-len
_function:
    
    ; creating a stack frame for the called program
    push ebp
    mov ebp, esp
    
    mov ecx, [ebp+8]
    mov esi, [ebp+12]
    mov edi, [ebp+16]
    std ; setting direction flag so that we can go in reverse order
    mov edx,0
    my_loop:
        lodsd
        shl eax, 1 ; multiply by 2
        mov [edi+edx],eax ;moving into destination string 
        add edx,4    
    loop my_loop
    
    
    ; restore the stack frame for the caller program
    mov esp, ebp
    pop ebp
    
    ret
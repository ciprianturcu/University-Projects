bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

extern exit, fopen, scanf, printf, fclose            
import exit msvcrt.dll
import fopen msvcrt.dll  
import scanf msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    x dd 0
    y dd 0
    format1 db "%d",0
    format2 db "%d",0
    max_length dd 0
    current_length dd 0
    result_string dd 0
    temp_string dd 0
    masca dd 5555h
    max_x dd 0
    max_y dd 0
    interval_print_format db " [%d,%d] ", 0
    special_number_print_format db "%h,", 0
    

; our code starts here
segment code use32 class=code
    start:
        cld
        push dword x
        push dword format1
        call [scanf]
        add esp, 4*2
        loop_citire_capete:
            push dword y
            push dword format2
            call [scanf]
            add esp,4*2
            mov ebx, [x]
            mov ecx,[y]
            cmp ebx,ecx  ;verificare ca nr sa fie citite in ordine crescatoare
            ja exit_citire
            mov dword [current_length],0
            loop_parcurgere:
                mov esi,ebx
                cmp ebx,ecx
                jnle exit_loop_parcurgere
                and ebx,[masca]
                mov ax,bx
                shr ebx,16
                mov dx,bx
                mov bx,3
                div bx
                cmp dx,0
                jne not_special
                inc dword [current_length]
                mov edi,temp_string
                stosd
                not_special:
                mov ebx,esi
                inc ebx
            jmp loop_parcurgere
            exit_loop_parcurgere:
            mov ebx, [current_length]
            mov edx, [max_length]
            cmp ebx,edx
            jnle maxim_fals
            mov dword [max_length], ebx
            mov edx,[x]
            mov dword [max_x], edx
            mov dword [max_y], ecx
            mov ecx,[current_length]
            store_loop:
            mov esi,temp_string
            mov edi,result_string
            lodsd
            stosd
            loop store_loop
            maxim_fals:
            mov dword [x],ecx
        jmp loop_citire_capete
        exit_citire:
        push dword max_x
        push dword max_y
        push dword interval_print_format
        call [printf]
        add esp, 4*3
        mov ecx,[max_length]
        print_loop:
            mov esi, result_string
            lodsd
            push eax
            push special_number_print_format
            call [printf]
            add esp, 4*2
        loop print_loop
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

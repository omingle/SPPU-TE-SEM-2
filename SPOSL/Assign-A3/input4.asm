	MACRO
	M1	&X, &Y, &A=AREG, &B=
	MOVER	&A, &X
	ADD	&A,	='1'
	MOVER	&B, &Y
	ADD	&B, ='5'
	MEND
	MACRO
	M2	&P, &Q, &U=CREG, &V=DREG
	MOVER	&U, &P
	MOVER	&V, &Q
	ADD	&U, ='15'
	ADD	&V, ='10'
	MEND
	START	100
	M1	10, 20, &B=CREG
	M2	100, 200, &V=AREG, &U=BREG
	END

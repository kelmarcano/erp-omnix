select inv_numdoc as numdoc,
inv_precio as totaldoc,
pag_frmpag as formapago,
pag_numfac as numpago,
pag_monto as montorecibido, SUM(dnpagos.pag_monto) AS TOTALRECIBIDO, IF(dnpagos.pag_frmpag='EFE',SUM(dnpagos.PAG_MONTO),0) as montoefectivo, 
IF(dnpagos.pag_frmpag='TDC',SUM(dnpagos.PAG_MONTO),0) as montotdc
from dninventario 
inner join dnpagos 
on inv_codmae = pag_codmae group by  pag_frmpag
order by formapago


SELECT SUM(dnpagos.PAG_MONTO) AS MTORECIBIDO FROM DNINVENTARIO inner join dnpagos 
on inv_codmae = pag_codmae

SELECT SUM(dnpagos.PAG_MONTO) AS MTOEFECTIVO FROM DNINVENTARIO inner join dnpagos 
on inv_codmae = pag_codmae WHERE pag_frmpag ='EFE' 

select inv_numdoc as numdoc,
inv_precio as totaldoc,
pag_frmpag as formapago,
pag_numfac as numpago,
pag_monto as montorecibido
from dninventario 
inner join dnpagos 
on inv_codmae = pag_codmae


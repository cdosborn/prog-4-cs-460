DELETE FROM cdosborn.visit v
WHERE EXISTS(
SELECT * FROM  cdosborn.appt a, cdosborn.patient p
WHERE v.appt#=a.appt# 
  AND a.patient#=p.patient#
  AND p.patient#=1);

DELETE FROM cdosborn.appt a
WHERE EXISTS(
SELECT * FROM cdosborn.patient p
WHERE a.patient#=p.patient#
  AND p.patient#=1);

DELETE FROM cdosborn.patient p WHERE p.patient#=1;


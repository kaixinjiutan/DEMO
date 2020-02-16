export const getDate = (time: number) => {
 if(!time) return;
 const DATE = new Date(time);
 const YEAR = DATE.getFullYear();
 const MONTH = DATE.getMonth() + 1;
 const DAY = DATE.getDate();
 const RESULT_DATE = `${YEAR}/${MONTH}/${DAY}`;
 return RESULT_DATE;
}
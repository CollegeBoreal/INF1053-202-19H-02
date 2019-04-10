package object daos {

  import slick.profile._

  implicit val localDateToDate: BaseColumnType[java.time.LocalDate] = MappedColumnType.base[java.time.LocalDate, java.sql.Date](
    l => java.sql.Date.valueOf(l),
    d => d.toLocalDate
  )


}

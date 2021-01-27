package top.ntutn.novelrecommend.utils


object AnnotationUtil {
    fun <T : Annotation> validAnnotation(clsList: List<Class<*>>, clazz: Class<T>): List<Class<*>> {
        return clsList.filter { it.getAnnotation(clazz) != null }
    }
}
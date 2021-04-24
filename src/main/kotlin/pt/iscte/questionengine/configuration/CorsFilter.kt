package pt.iscte.questionengine.configuration

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val response = servletResponse as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "*")
        response.setHeader("Access-Control-Allow-Headers", "*")
        response.setHeader("Access-Control-Max-Age", "3600")
        if ("OPTIONS".equals((servletRequest as HttpServletRequest).method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain.doFilter(servletRequest, servletResponse)
        }
    }
}

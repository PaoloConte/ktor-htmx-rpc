package app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.paoloconte.htmx.dsl.*
import io.paoloconte.htmx.rpc.RpcRouting.Companion.rpc
import pages.*
import service.ContactRepository

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureRouting()
    }.start(wait = true)
}

fun Application.configureRouting() {
    routing {
        // Serve static files (CSS, JS)
        staticResources("/static", "static")

        get("/") {
            call.respondHtml {
                fullPage("Contacts") {
                    contactPageLayout()
                }
            }
        }

        rpc {
            registerAll(ContactPage)
          //  ContactPage.register()
        }
    }
}

fun Tag.fullPage(title: String, content: Tag.() -> Unit) {
    html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1")
            title { +title }

            // HTMX
            script(src = "https://unpkg.com/htmx.org@2.0.4") {}

            // Minimal CSS (in production use Tailwind or similar)
            style {
                raw("""
                * { box-sizing: border-box; margin: 0; padding: 0; }
                body { font-family: system-ui, sans-serif; background: #f5f5f5; color: #333; }

                .container { max-width: 960px; margin: 0 auto; padding: 2rem; }
                .card { background: white; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
                .mb-4 { margin-bottom: 1rem; }
                .mr-4 { margin-right: 1rem; }
                .ml-2 { margin-left: 0.5rem; }
                .p-4 { padding: 1rem; }
                .px-4 { padding-left: 1rem; padding-right: 1rem; }
                .py-2 { padding-top: 0.5rem; padding-bottom: 0.5rem; }
                .py-3 { padding-left: 0.75rem; padding-right: 0.75rem; }
                .py-8 { padding-top: 2rem; padding-bottom: 2rem; }
                .mt-3 { margin-top: 0.75rem; }
                .w-full { width: 100%; }
                .relative { position: relative; }
                .text-sm { font-size: 0.875rem; }
                .text-lg { font-size: 1.125rem; }
                .text-left { text-align: left; }
                .text-right { text-align: right; }
                .text-center { text-align: center; }
                .font-bold { font-weight: bold; }
                .font-medium { font-weight: 500; }
                .text-gray-500 { color: #6b7280; }
                .text-gray-600 { color: #4b5563; }
                .text-red-500 { color: #ef4444; }

                .flex { display: flex; }
                .gap-4 { gap: 1rem; }
                .grid { display: grid; }
                .grid-cols-3 { grid-template-columns: repeat(3, 1fr); }

                .input {
                    border: 1px solid #d1d5db; border-radius: 4px; padding: 0.5rem 0.75rem;
                    font-size: 0.875rem; width: 100%;
                }
                .input:focus { outline: none; border-color: #3b82f6; box-shadow: 0 0 0 2px rgba(59,130,246,0.2); }

                .btn {
                    display: inline-block; padding: 0.5rem 1rem; border: none; border-radius: 4px;
                    font-size: 0.875rem; cursor: pointer; transition: opacity 0.2s;
                }
                .btn:hover { opacity: 0.85; }
                .btn-sm { padding: 0.25rem 0.75rem; font-size: 0.8rem; }
                .btn-primary { background: #3b82f6; color: white; }
                .btn-secondary { background: #6b7280; color: white; }
                .btn-success { background: #10b981; color: white; }
                .btn-danger { background: #ef4444; color: white; }
                .btn-icon { background: none; border: none; cursor: pointer; font-size: 1.2rem; }

                .table { width: 100%; border-collapse: collapse; }
                .table thead { background: #f9fafb; border-bottom: 2px solid #e5e7eb; }
                .table tbody tr { border-bottom: 1px solid #e5e7eb; transition: background 0.15s; }
                .table tbody tr:hover { background: #f9fafb; }
                .table tbody tr.editing { background: #eff6ff; }

                .block { display: block; }

                /* Toast */
                #toast-area { position: fixed; top: 1rem; right: 1rem; z-index: 1000; }
                .toast {
                    padding: 0.75rem 1.5rem; border-radius: 6px; margin-bottom: 0.5rem;
                    color: white; font-size: 0.875rem; animation: slideIn 0.3s ease;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                }
                .toast-success { background: #10b981; }
                .toast-error { background: #ef4444; }
                .toast-warning { background: #f59e0b; }
                .toast-info { background: #3b82f6; }
                @keyframes slideIn { from { transform: translateX(100%); opacity: 0; } to { transform: translateX(0); opacity: 1; } }

                /* HTMX indicator */
                .htmx-indicator { display: none; }
                .htmx-request .htmx-indicator, .htmx-request.htmx-indicator { display: inline; }
                .search-spinner.htmx-indicator { display: none; }
                .htmx-request .search-spinner.htmx-indicator, .htmx-request.search-spinner.htmx-indicator { display: block; position: absolute; right: 0.75rem; top: 50%; transform: translateY(-50%); font-size: 0.875rem; color: #6b7280; }

                /* Header */
                header { background: white; border-bottom: 1px solid #e5e7eb; padding: 1rem 0; margin-bottom: 2rem; }
                header h1 { font-size: 1.5rem; }
                """.trimIndent())
            }
        }

        body {
            // Toast area globale (target per OOB)
            div { id = "toast-area" }

            header {
                div("container") {
                    h1 { +"Contact Manager" }
                }
            }

            div("container") {
                content()
            }

            // Auto-dismiss toast after 3 seconds
            script {
                raw("""
                var toastArea = document.getElementById('toast-area');
                if (toastArea) {
                    new MutationObserver(function() {
                        toastArea.querySelectorAll('.toast[data-auto-dismiss]:not([data-dismiss-scheduled])').forEach(function(toast) {
                            toast.setAttribute('data-dismiss-scheduled', 'true');
                            var ms = parseInt(toast.getAttribute('data-auto-dismiss'));
                            setTimeout(function() {
                                toast.style.opacity = '0';
                                toast.style.transition = 'opacity 0.3s';
                                setTimeout(function() { toast.remove(); }, 300);
                            }, ms);
                        });
                    }).observe(toastArea, { childList: true, subtree: true });
                }
                """.trimIndent())
            }

            // HTMX error handling — show toast on server errors and network failures
            script {
                raw("""
                document.body.addEventListener('htmx:responseError', function(e) {
                    showErrorToast('Request failed: ' + e.detail.xhr.status);
                });
                document.body.addEventListener('htmx:sendError', function() {
                    showErrorToast('Connection lost');
                });
                function showErrorToast(msg) {
                    var area = document.getElementById('toast-area');
                    if (!area) return;
                    var toast = document.createElement('div');
                    toast.className = 'toast toast-error';
                    toast.setAttribute('data-auto-dismiss', '3000');
                    toast.textContent = msg;
                    area.prepend(toast);
                }
                """.trimIndent())
            }
        }
    }
}

fun Tag.contactPageLayout() {
    val contacts = ContactRepository.findAll()

    statsBar()

    searchBar()

    addContactForm()

    div("card") {
        contactTable(contacts)
    }
}
